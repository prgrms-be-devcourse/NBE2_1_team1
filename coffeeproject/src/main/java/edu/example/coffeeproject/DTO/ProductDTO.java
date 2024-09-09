package edu.example.coffeeproject.DTO;

import edu.example.coffeeproject.entity.enums.Category;
import edu.example.coffeeproject.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long productId;

    private String productName;

    private String category;

    private int price;

    private String description;

    // Product를 매개변수로 받아서, ProductDTO의 필드들을 초기화하는 생성자
    public ProductDTO(Product product) {

        this.productId = product.getProductId();
        this.productName = product.getProductName();

        // ".name()" -> enum 상수를 문자열로 변환
        // ".valueOf()" -> 문자열을 enum 상수로 변환
        // 해당 문자열이 enum에 없는 경우 IllegalArgumentException 예외 발생
        this.category = product.getCategory().name();

        this.price = product.getPrice();
        this.description = product.getDescription();

    }

    //    ProductDTO로 받은 값을 Product에 저장한 후, 반환하는 메서드
    public Product toEntity() {

        Product product = Product.builder().productId(productId)
                                 .productName(productName)
                                 .price(price)
                                 .description(description)
                                 .build();

        // PostMapping에서 categort가 Null로 나오는 문제해결
        product.changeCategory(category);

        return product;
    }

}
