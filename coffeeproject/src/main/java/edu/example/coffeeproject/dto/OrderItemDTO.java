package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static edu.example.coffeeproject.entity.QOrderItem.orderItem;

@Data
@Builder
public class OrderItemDTO {
    @Schema(description = "주문 상품 Id")
    private Long orderItemId;
    @Schema(description = "상품 식별 Id")
    private Long productId;
    @Schema(description = "상품 카테고리")
    private Category category;
    @Schema(description = "상품 개당 가격")
    private int price;
    @Schema(description = "주문 상품 수량")
    private int quantity;

}
