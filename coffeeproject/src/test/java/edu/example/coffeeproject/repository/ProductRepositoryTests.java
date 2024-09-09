package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.entity.enums.Category;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        // GIVEN: 각 필드를 임의의 변수에 저장
        String productName = "Premium Coffee Beans";     // 상품명
        Category productCategory = Category.COFFEE_BEAN_PACKAGE;  // 상품 카테고리
        int price = 10000;                               // 상품 가격
        String description = "에티오피아산 산미 로스팅 원두 제조일자 : 2024/01/01"; // 상품 설명

        // Product 객체 생성에 위 변수를 사용
        Product product = Product.builder()
                                 .productName(productName)
                                 .category(productCategory)
                                 .price(price)
                                 .description(description)
                                 .build();

        // WHEN: 상품을 저장
        productRepository.save(product);

        // THEN: 로그를 통해 저장된 상품을 확인
        log.info("1" + product);
    }

}

