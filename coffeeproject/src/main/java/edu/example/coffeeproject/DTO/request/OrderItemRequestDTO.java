package edu.example.coffeeproject.DTO.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {

    @NotNull(message = "값을 입력해주세요.")
    private Long productId;

    @Min(value = 1, message = "주문 수량은 최소 1 입니다.")
    private int quantity;
    }

// 주문할 때(Order), OrderItemList에 담을 필드
// productId 하나로 데이터들 다 가져옴
// int는 null이 되지 않기 때문에 @NotNull 불필요