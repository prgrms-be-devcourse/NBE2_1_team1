package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionIdJavaType;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class OrderItem implements Comparable<OrderItem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    private Category category;
    private int price;
    private int quantity;


    @Override
    public int compareTo(OrderItem o) {
        return this.orderId - o.orderId;
    }
}
