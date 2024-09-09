package edu.example.coffeeproject.entity;

import edu.example.coffeeproject.entity.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tbl_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId; // 주문한 상품 정보 ID

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;        // 상품 정보 (Product 엔티티 참조)

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;            // 주문 정보 (Order 엔티티 참조)

    @Enumerated(EnumType.STRING)
    private Category category;      // 상품의 카테고리(Service에서 Product 엔티티를 통해 가져옴)

    private int price;              // 상품의 가격(Service에서 Product 엔티티를 통해 가져옴)

    private int quantity;           // 수량(수량에 따라 가격 바뀌는 메서드 추가 예정)


    // 수량에 따른 가격 계산 (가격 x 수량)
    public int totalPrice() {
        return this.price * this.quantity;

    }

}

//    @Override
//    public int compareTo(OrderItem o) {
//        return this.orderId - o.orderId;
//    }
    // 엔티티로 바뀌어서,
    // 임베더블 어노테이션 compareTo 메서드 주석처리