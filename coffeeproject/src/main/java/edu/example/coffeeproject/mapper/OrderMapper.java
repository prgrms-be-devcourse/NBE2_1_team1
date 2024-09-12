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

                // 클라이언트로부터 받은 orderItem 데이터로
                // product에서 가져온 price * orderItem 엔티티의 quantity를 계산
                // 이런 비즈니스 로직은 서비스에서 처리하는게 나음 + 밑에 메서드도
                .totalPrice(orderItems.stream().mapToInt(
                        orderItem -> orderItem.getProduct().getPrice()*
                                     orderItem.getQuantity()).sum())

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
                        // -> ".map(OrderItemMapper::toOrderItemResponseDTO)" 이렇게 대체 가능하지만, 이해 필요
                        .collect(Collectors.toList()))
                        // ".collect(Collectors.toList()" -> ".toList" 이렇게 대체 가능하지만, 이해 필요
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}