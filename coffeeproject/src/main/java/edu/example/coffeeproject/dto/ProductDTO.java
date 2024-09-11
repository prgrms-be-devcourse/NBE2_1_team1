package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.entity.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDTO {
    @NotEmpty
    private Long productId;

    @NotEmpty
    private Category category;

    @NotEmpty
    private String productName;

    @Min(0)
    private int price;

    @NotEmpty
    private String description;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ProductDTO(Product product){
        this.productId = product.getProductId();
        this.category = product.getCategory();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.createDate = product.getCreateDate();
        this.updateDate = product.getUpdateDate();
    }

    public Product toEntity(){
        Product product = Product.builder().productId(productId)
                                            .category(category)
                                            .productName(productName)
                                            .price(price)
                                            .description(description)
                                            .build();

        return product;
    }
}
