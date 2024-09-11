package edu.example.coffeeproject.DTO.response;

import edu.example.coffeeproject.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int totalPrice;          // 전체 가격 -> Mapper에서 구현
    private List<OrderItemResponseDTO> orderItems;
}