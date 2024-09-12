package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    @Schema(description = "상품 Id")
    private Long productId;
    @Schema(description = "상품 이름")
    @NotBlank(message = "상품 이름은 비어있을 수 없습니다.")
    private String productName;
    @Schema(description = "상품 카테고리")
    @NotBlank(message = "상품 카테고리는 비어있을 수 없습니다.")
    private String category;
    @Schema(description = "상품 가격")
    @Min(0)
    @NotEmpty(message = "상품 가격은 비어있을 수 없습니다.")
    private int price;
    @Schema(description = "상품 설명")
    @NotBlank(message = "상품 설명은 비어있을 수 없습니다.")
    private String description;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory().name();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Product toEntity() {
        Product product = Product.builder().productId(productId)
                .productName(productName)
                .price(price)
                .description(description)
                .build();
        product.changeCategory(category);

        return product;
    }
}
