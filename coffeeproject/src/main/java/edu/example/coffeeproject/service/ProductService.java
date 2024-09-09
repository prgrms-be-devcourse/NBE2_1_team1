package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.dto.UpdateProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    //상품 등록
    public ProductDTO register(ProductDTO productDTO){
        try{
            Product product = productDTO.toEntity();
            productRepository.save(product);
            return new ProductDTO(product);
        } catch (Exception e){
            log.error("--- "+ e.getMessage());
            throw ProductException.NOT_REGISTERED.get();
        }
    }

    //전체 상품 조회
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    //상품 상세 설명
    public Product findById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("not found : "+productId));
    }

    //상품 삭제
    public void delete(Long productId){
        productRepository.deleteById(productId);
    }

    //상품 수정
    @Transactional
    public Product update(Long productId, UpdateProductDTO updateProductDTO){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + productId));

        product.update(updateProductDTO.getProductName(), updateProductDTO.getPrice(), updateProductDTO.getDescription());

        return product;
    }
}
