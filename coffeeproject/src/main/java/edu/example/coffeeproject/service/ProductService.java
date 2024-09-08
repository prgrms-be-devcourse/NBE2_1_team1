package edu.example.coffeeproject.service;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductNotFoundException;
import edu.example.coffeeproject.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

//    @Autowired
//    private ProductRepository productRepository;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}