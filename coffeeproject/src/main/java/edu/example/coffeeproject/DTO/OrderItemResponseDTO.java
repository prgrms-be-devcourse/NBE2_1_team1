package edu.example.coffeeproject.DTO;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private int perPrice;
//    private int totalPrice; 나중에 구현
}