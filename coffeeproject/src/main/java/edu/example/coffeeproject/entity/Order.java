package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    private String postCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostCode(String postCode) {
        this.postCode = postCode;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
