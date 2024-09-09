package edu.example.coffeeproject.entity;

import edu.example.coffeeproject.entity.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_product")
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @Enumerated(EnumType.STRING)    //Enum을 데이터베이스에 저장할 때, String 형식으로 저장
    private Category category;

    private int price;
    private String description;

    @CreatedDate            //상품 등록 일시
    private LocalDateTime createDate;

    @LastModifiedDate       //상품 수정 일시
    private LocalDateTime updateDate;


    public void update(String productName, int price, String description){
        this.productName = productName;
        this.price = price;
        this.description = description;
    }
}


