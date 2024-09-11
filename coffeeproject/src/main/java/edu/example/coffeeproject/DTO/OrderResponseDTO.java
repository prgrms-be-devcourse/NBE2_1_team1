package edu.example.coffeeproject.DTO;

import edu.example.coffeeproject.entity.OrderStatus;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus; // String으로 안해도 되는지??
    private LocalDateTime createdAt;
    private int totalQuantity;
    private List<OrderItemResponseDTO> orderItems; // 주문한 상품의 데이터 일람
}

// 주문이 끝난 클라이언트에게 보여줄 데이터들
// 수량 별 금액 계산 메서드 추가 예정