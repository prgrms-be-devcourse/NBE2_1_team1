package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductNotFoundException;
import edu.example.coffeeproject.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productDTO.toEntity(); // DTO에서 꼭 필요한 정보만 product 엔티티에 저장
        Product savedProduct = productRepository.save(product);
        return ProductDTO.fromEntity(savedProduct); // DTO를 사용하여 product엔티티에서 외부로 반환해도 괜찮은 정보들만 반환
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDTO::fromEntity);
    }

    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ProductDTO.fromEntity(product);
    }

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId) // Product 영속성 엔티티
                .orElseThrow(() -> new ProductNotFoundException(productId));

        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(productDTO.getCategory());

        // 기존의 상품 데이터에 대한 갱신을 마친 다음 DTO를 사용하여 꼭 필요한 정보만 반환
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.fromEntity(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.deleteById(productId);
    }
}
