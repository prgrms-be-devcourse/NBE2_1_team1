package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.entity.enums.Category;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        for (int i = 0; i < 10; i++) {
            // GIVEN: 각 필드를 임의의 변수에 저장
            String productName = "Premium Coffee Beans" + i;             // 상품명
            Category productCategory = Category.COFFEE_BEAN_PACKAGE;     // 상품 카테고리
            int price = 12000 + (i * 100);                               // 상품 가격
            String description = "좋은 원두 제조일자 : 2024/01/01";      // 상품 설명

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
            log.info("product : " + product);
        }

    }

    @Test
    public void testFindById() {

        Long productId = 1L;

        Optional<Product> foundProduct = productRepository.findById(productId);

        log.info("foundProduct : " + foundProduct.get());

    }

    @Test
    public void testUpdate() {

        Long productId = 1L;
        String productName = "(품절)";
        int price = 0;
        String description = "(재입고 예정)";

        Optional<Product> foundProduct = productRepository.findById(productId);

        Product product = foundProduct.get();
        product.changeProductName(productName);
        product.changePrice(price);
        product.changeDescription(description);

        productRepository.save(product);
        log.info("updated product : " + foundProduct.get());

    }

    @Test
    public void testDelete() {

        Long productId = 1L;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent());

        productRepository.deleteById(productId);

        assertTrue(foundProduct.isEmpty());

    }

    @Test
    public void testFindAll() {

        // 1페이지(인덱스 0부터 시작)에서 5개의 상품을 가격순으로 내림차순 정렬하여 가져오는 페이지 요청 설정
        Pageable pageable = PageRequest.of(0, 5, Sort.by("productId").ascending());

        // 위 페이지 설정에 따라 상품 리스트 가져옴
        Page<Product> foundList = productRepository.findAll(pageable);

        //
        foundList.getContent().forEach(product -> log.info("Product: " + product));

        log.info("전체 상품 수 : " + foundList.getTotalElements()); // 전체 상품 개수
        log.info("전체 페이지 : " + foundList.getTotalPages());     // 전체 페이지 개수
        log.info("현재 페이지 : " + foundList.getNumber());         // 현재 페이지 번호
        log.info("한 페이지당 게시물 수 : " + foundList.getSize()); // 페이지 크기 (한 페이지에 포함된 상품 개수)

    }

}

