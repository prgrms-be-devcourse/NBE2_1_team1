package edu.example.coffeeproject.entity;

import edu.example.coffeeproject.entity.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="tbl_product")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;             // 상품 아이디

    private String productName;         // 상품명

                                        // 이 어노테이션을 지우면 에러는 없지만,
                                        // enum값을 순서에 따른 "숫자"로 저장함
    @Enumerated(EnumType.STRING)        // 예를 들어, enum값에 2개의 문자열을 각각 "0,1"로 반환함
    private Category category;          // 상품의 카테고리(enum 클래스에 따로 정의)

    private int price;                  // 상품의 가격
    private String description;         // 상품의 원산지 및 정보

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeCategory(String categoryName) {
        this.category = Category.valueOf(categoryName);
    }

}
