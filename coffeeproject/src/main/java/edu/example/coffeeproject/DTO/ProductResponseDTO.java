package edu.example.coffeeproject.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor     // @builder 쓰기위해 추가
@Builder                // Mapper에서 .builder 쓰기위해 추가
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private String category;
    @Min(0)
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

// 서버에 등록한 상품의 정보를 조회 할 때, 클라이언트에게 보낼 데이터