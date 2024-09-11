package edu.example.coffeeproject.DTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
}