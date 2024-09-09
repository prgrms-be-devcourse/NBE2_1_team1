package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderItem")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemId;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name="orderId")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;
    private int price;
    private int quantity;
}