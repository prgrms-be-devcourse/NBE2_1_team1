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
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static edu.example.coffeeproject.entity.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 상품_등록() {
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
    public void 상품_목록_보기() {
        Pageable pageable = PageRequest.of(0, //페이지 번호 - 첫번째 페이지는 0부터 시작
                3 //한 페이지 게시물의 숫자
                , Sort.by("productId").descending());

        Page<ProductDTO> productList = productRepository.list(pageable);
        assertNotNull(productList);
        assertEquals(5, productList.getTotalElements());  //전체 게시물 수
        assertEquals(2, productList.getTotalPages());  //총 페이지 수 10개
        assertEquals(0, productList.getNumber());     //현재 페이지 번호 0
        assertEquals(3, productList.getSize());    //한 페이지 게시물 수 10개

        productList.getContent().forEach(System.out::println);
    }

    @Test
    @Transactional
    @Commit
    public void 상품_수정() {
        Long productId = 1L;
        String productName = "변경 상품";
//        String category = "COFFEE_PORT_PACKAGE";   //카테고리 변경이 안됌
        int price = 7000;
        String description = "커피 포트입니다.";

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");
        foundProduct.get().changeProductName(productName);
//        foundProduct.get().changeCategory(category);
        foundProduct.get().changePrice(price);
        foundProduct.get().changeDescription(description);

        foundProduct = productRepository.findById(productId);
        assertEquals(price, foundProduct.get().getPrice());
//        assertEquals(category, foundProduct.get().getCategory().name());

    }

    @Test
    @Transactional
    @Commit
    public void testDelete() {
        Long productId = 4L;

        assertTrue(productRepository.findById(productId).isPresent(), "Product should be present");  //1. pno에 해당하는 Product 객체가 존재하는지 검증

        productRepository.deleteById(productId);//2. pno에 해당하는 Product 객체 삭제

        assertFalse(productRepository.findById(productId).isPresent(), "Product should be present");//3. pno에 해당하는 Product 객체가 존재하지 않는 것을 검증
    }


}
