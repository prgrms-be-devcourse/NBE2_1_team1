package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private Category category;
    private int price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderItem toEntity() {
        return OrderItem.builder()
                .productId(this.productId)
                .quantity(this.quantity)
                .build();
    }

    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProductId())
                .category(orderItem.getCategory())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .createdAt(orderItem.getCreatedAt())
                .updatedAt(orderItem.getUpdatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "상품번호 = '" + productId + '\'' +
                ", 가격 = " + price +
                ", 카테고리 = '" + category + '\'' +
                ", 수량 = " + quantity +
                '}';
    }
}
