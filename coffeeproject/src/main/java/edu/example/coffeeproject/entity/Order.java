package edu.example.coffeeproject.entity;

import edu.example.coffeeproject.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name="tbl_order")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;                // 주문ID

    private String email;                // 주문자 이메일
    private String address;              // 배송지 주소
    private String postcode;             // 배송지 우편번호

    @OneToMany
    @JoinColumn(name = "order_id")
    @Builder.Default
    private SortedSet<OrderItem> orderItems = new TreeSet<>();  // 주문 상품 목록

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;     // 주문 상태 (enum으로 정의)

//    @CreatedDate
//    private LocalDateTime orderDate;     // 주문 일시

}