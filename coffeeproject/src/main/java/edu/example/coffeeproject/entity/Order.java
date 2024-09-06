package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name="tbl_order")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String email;
    private String address;
    private String postcode;

    private SortedSet<OrderItem> orderItems = new TreeSet<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
