package edu.example.coffeeproject.mapper;

import edu.example.coffeeproject.DTO.ProductRequestDTO;
import edu.example.coffeeproject.DTO.ProductResponseDTO;
import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;

public class ProductMapper {

    // RequestDTO -> Entity 변환
    public static Product toProductEntity(ProductRequestDTO productRequest) {
        return Product.builder()
                      .productName(productRequest.getProductName())
                      .price(productRequest.getPrice())
                      .category(Category.valueOf(productRequest.getCategory()))
                      .description(productRequest.getDescription())
                      .build();
    }

    // Entity -> ResponseDTO 변환
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                                 .productId(product.getProductId())
                                 .productName(product.getProductName())
                                 .price(product.getPrice())

                                 .category(product.getCategory() != null ? product.getCategory().name() : "UNKNOWN")
                                  // category(enum)이 null이 아니면 문자열로 변환, -> ".name()"
                                  // null이면 UNKNOWN 지정 -> 지정 안하는 에러 고치기

                                 .description(product.getDescription())
                                 .createdAt(product.getCreatedAt())
                                 .updatedAt(product.getUpdatedAt())
                                 .build();
    }

}