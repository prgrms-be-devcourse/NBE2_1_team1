package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static edu.example.coffeeproject.entity.QOrderItem.orderItem;

@Data
@Builder
public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private Category category;
    private int price;
    private int quantity;

}
