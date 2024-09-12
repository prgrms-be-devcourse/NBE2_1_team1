package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Long productId;
    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Size(min = 2, max = 50, message = "상품 이름은 비어 있지 않아야 하며 2-50자 사이여야 합니다.")
    private String productName;
    @Positive(message = "가격은 양수여야 합니다.")
    private int price;
    @Size(max = 300, message = "설명은 300자를 넘지 않아야 합니다.")
    private String description;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .price(this.price)
                .description(this.description)
                .category(this.category)
                .build();
    }

    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "상품명 = '" + productName + '\'' +
                ", 가격 = " + price +
                ", 상세설명 = '" + description + '\'' +
                ", 카테고리 = '" + category + '\'' +
                '}';
    }
}
