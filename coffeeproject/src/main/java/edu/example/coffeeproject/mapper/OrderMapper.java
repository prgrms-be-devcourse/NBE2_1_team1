package edu.example.coffeeproject.mapper;

import edu.example.coffeeproject.DTO.request.OrderRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderResponseDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    // RequestDTO -> Order Entity 변환
    public static Order toOrderEntity(OrderRequestDTO orderRequestDTO, List<OrderItem> orderItems) {
        return Order.builder()
                .email(orderRequestDTO.getEmail())
                .address(orderRequestDTO.getAddress())
                .postcode(orderRequestDTO.getPostcode())
                .orderItems(orderItems)
                .build();
    }

    // Order Entity -> OrderResponseDTO 변환
    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getOrderItems().stream()
                        .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                        .sum())
                .orderItems(order.getOrderItems().stream()
                        .map(orderItem -> OrderItemMapper.toOrderItemResponseDTO(orderItem))
                        .collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}