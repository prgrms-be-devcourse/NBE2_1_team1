package edu.example.coffeeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateProductDTO {
    private String productName;
    private int price;
    private String description;
}
