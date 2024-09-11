package edu.example.coffeeproject.mapper;

import edu.example.coffeeproject.DTO.request.OrderItemRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderItemResponseDTO;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;

public class OrderItemMapper {

    // OrderItemRequestDTO -> OrderItem Entity 변환
    public static OrderItem toOrderItemEntity(OrderItemRequestDTO orderItemRequestDTO, Product product) {
        return OrderItem.builder()
                .product(product)
//              .order(order) -> 서비스에서 지정
                .price(product.getPrice())
                .quantity(orderItemRequestDTO.getQuantity())
                .build();
    } // 이 toEntity 코드에서 product, order 받는 흐름이 살짝 이해 X

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