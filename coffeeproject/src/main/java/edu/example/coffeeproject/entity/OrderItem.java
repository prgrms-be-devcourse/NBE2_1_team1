package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;
    private int quantity;

}
