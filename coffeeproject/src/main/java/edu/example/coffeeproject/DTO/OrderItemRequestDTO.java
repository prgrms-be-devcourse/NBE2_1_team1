package edu.example.coffeeproject.DTO;


import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemRequestDTO {
        private Long productId;
        @Min(1)
        private int quantity;
    }

// 주문할 때(Order), OrderItemList에 담을 필드
// productId 하나로 데이터들 다 가져옴
// 복수 종류의 productId를 복수로 살 수 있는 기능 나중에 구현