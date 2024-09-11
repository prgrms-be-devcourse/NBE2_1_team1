package edu.example.coffeeproject.mapper;

import edu.example.coffeeproject.DTO.request.ProductRequestDTO;
import edu.example.coffeeproject.DTO.response.ProductResponseDTO;
import edu.example.coffeeproject.entity.Product;

public class ProductMapper {

    // RequestDTO -> Entity 변환
    public static Product toProductEntity(ProductRequestDTO productRequest) {
        return Product.builder()
                      .productName(productRequest.getProductName())
                      .price(productRequest.getPrice())
                      .category(productRequest.getCategory())
                      .description(productRequest.getDescription())
                      .build();
    }

    // Entity -> ResponseDTO 변환
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                                 .productId(product.getProductId())
                                 .productName(product.getProductName())
                                 .price(product.getPrice())
                                 .category(product.getCategory())
                                 .description(product.getDescription())
                                 .createdAt(product.getCreatedAt())
                                 .updatedAt(product.getUpdatedAt())
                                 .build();
    }

}