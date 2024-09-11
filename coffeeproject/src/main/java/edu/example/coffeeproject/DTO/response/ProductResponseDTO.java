package edu.example.coffeeproject.DTO.response;

import edu.example.coffeeproject.entity.Category;
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
    private Category category;
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}