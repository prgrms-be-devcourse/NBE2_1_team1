package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class ProductListDTO {

    private Long productId;
    private String productName;
    private Category category;
    private int price;
    private String description;
}
