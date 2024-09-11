package edu.example.coffeeproject.DTO.request;

import edu.example.coffeeproject.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "값을 입력해주세요.")
    private String productName;

    @NotNull(message = "값을 입력해주세요.")
    private Category category;

    @NotNull(message = "값을 입력해주세요.")
    @Min(value = 0, message = "0 이상을 입력해주세요")
    private int price;

    @NotBlank(message = "값을 입력해주세요.")
    private String description;
}

// 서버에 상품을 등록할 때, 클라이언트가 보낼 데이터