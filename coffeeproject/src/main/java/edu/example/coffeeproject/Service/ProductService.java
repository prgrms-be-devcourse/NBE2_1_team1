package edu.example.coffeeproject.Service;

import edu.example.coffeeproject.DTO.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;


    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        productRepository.save(product);
        return new ProductDTO(product);
        // 타입이 달라서 "return productRepository.save(product);"
        // 오류나는 것은 알겠으나,
        // "return new ProductDTO(product);" 이 코드 해석필요!!
    }

    public ProductDTO getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return new ProductDTO(product.get());
//      return new ProductDTO(product.orElse(null));
        // 왜 인텔리제이가 아래의 코드를 추천하는지
    }

//    public List<ProductDTO> getAllProducts() {
//    }

}
