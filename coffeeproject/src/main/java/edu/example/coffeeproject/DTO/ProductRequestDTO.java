package edu.example.coffeeproject.DTO;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    private String productName;
    private String category;
    @Min(0)
    private int price;
    private String description;
}

// 서버에 상품을 등록할 때, 클라이언트가 보낼 데이터