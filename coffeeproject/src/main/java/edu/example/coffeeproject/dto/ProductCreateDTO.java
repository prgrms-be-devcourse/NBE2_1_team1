package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductCreateDTO {
    private String productName;
    private int price;
    private String description;
    private Category category;

    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .price(this.price)
                .description(this.description)
                .category(this.category)
                .build();
    }
}