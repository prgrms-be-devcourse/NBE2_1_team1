package edu.example.coffeeproject.DTO.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {

    @NotNull(message = "상품 번호를 입력해주세요.")
    private Long productId;

    @Min(value = 1, message = "주문 수량은 최소 1 입니다.")
    private int quantity;
    }

// 주문할 때는 클라이언트가 서버에게 상품 번호와 갯수만 보내지만, -> 노션에 정리
// 주문이 완료되면 서버는 클라이언트에게 상품 번호로 조회환 product의 다양한 데이터를 보냄
// int는 null이 되지 않기 때문에 @NotNull 불필요