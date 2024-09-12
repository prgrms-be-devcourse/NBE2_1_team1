package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order toEntity() {
        return Order.builder()
                .email(this.email)
                .address(this.address)
                .postcode(this.postcode)
                .orderItems(this.orderItems.stream()
                        .map(OrderItemDTO::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static OrderDTO fromEntity(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .orderStatus(order.getOrderStatus())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemDTO::fromEntity)
                        .collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "이메일 = '" + email + '\'' +
                ", 우편번호 = '" + address + '\'' +
                ", 우편번호 = '" + postcode + '\'' +
                ", 주문상세 = '" + orderItems + '\'' +
                '}';
    }
}
