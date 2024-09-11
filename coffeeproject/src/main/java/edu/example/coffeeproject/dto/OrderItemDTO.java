package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.enums.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Long productId;
    private Category category;
    private int price;
    private int quantity;
}
