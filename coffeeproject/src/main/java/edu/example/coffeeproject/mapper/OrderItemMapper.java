package edu.example.coffeeproject.mapper;

import edu.example.coffeeproject.DTO.request.OrderItemRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderItemResponseDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;

public class OrderItemMapper {

    // OrderItemRequestDTO -> OrderItem Entity 변환
    public static OrderItem toOrderItemEntity(OrderItemRequestDTO orderItemRequestDTO,Product product, Order order) {
        return OrderItem.builder()
                .product(product) // 서비스에서 product 조회해서 넘겨줌
                .order(order)     // 서비스에서 order 설정해서 넘겨줌
                .price(product.getPrice())
                .quantity(orderItemRequestDTO.getQuantity())
                .build();
    }

    // OrderItem Entity -> OrderItemResponseDTO 변환
    public static OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem) {
        return OrderItemResponseDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getProductId())
                .productName(orderItem.getProduct().getProductName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

}