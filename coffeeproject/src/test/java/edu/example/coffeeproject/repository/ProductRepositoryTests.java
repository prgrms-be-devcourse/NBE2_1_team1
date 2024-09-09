package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

import static edu.example.coffeeproject.entity.Category.COFFEE_BEAN_PACKAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Product product = Product.builder()
                    .productName("신규 상품_" + i)
                    .category(COFFEE_BEAN_PACKAGE)
                    .price(10000)
                    .description("상품 설명" + i)
                    .build();

            //WHEN - 엔티티 저장
            Product savedProduct = productRepository.save(product);

            //THEN - savedProduct가 Null이 아니고 mno는 1일 것
            assertNotNull(savedProduct);
            assertEquals(i, savedProduct.getProductId());
        });
    }

    @Test   //@Query 테스트
    public void 전체_목록_보기() {
        List<Product> productList = productRepository.findAll();

    }



}
