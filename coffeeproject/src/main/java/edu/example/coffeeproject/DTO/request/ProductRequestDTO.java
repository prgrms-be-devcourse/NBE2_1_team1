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

    @NotBlank(message = "상품 명을 입력해주세요.")
    private String productName;

    @NotNull(message = "카테고리를 입력해주세요.")
    private Category category;

//  @NotNull(message = "가격을 값을 입력해주세요.") -> int는 NotNull이 의미가 없음
    @Min(value = 0, message = "0 보다 큰 값을 입력해주세요")
    private int price;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String description;
}