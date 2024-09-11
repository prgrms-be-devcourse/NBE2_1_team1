package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String category;
    @Min(0)
    private int price;
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
