package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.ProductCreateDTO;
import edu.example.coffeeproject.dto.ProductResponseDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductNotFoundException;
import edu.example.coffeeproject.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

//    @Autowired
//    ProductService productService;

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping("/create")
//    public Product createProduct(@RequestBody Product product) {
//        return productService.saveProduct(product);
//    }
    // DTO 적용 여부 논의 필요
    @PostMapping("/create")
    public ProductResponseDTO createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product savedProduct = productService.saveProduct(productCreateDTO.toEntity());
        return ProductResponseDTO.fromEntity(savedProduct);
    }

//    @GetMapping("/list")
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }

    @GetMapping("/list")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @GetMapping("/{productId}")
//    public Product getProductById(@PathVariable Long productId) {
//        return productService.getProductById(productId);
//    }

    @GetMapping("/{productId}")
    public ProductResponseDTO getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return ProductResponseDTO.fromEntity(product);
    }

//    @PutMapping("/{productId}")
//    public Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
//        Product updatedProduct = productService.updateProduct(productId, product);
//        if (updatedProduct != null) {
//            return updatedProduct;
//        } else {
//            throw new ProductNotFoundException(productId);
//        }
//    }

    @PutMapping("/{productId}")
    public ProductResponseDTO updateProduct(@PathVariable Long productId, @RequestBody ProductCreateDTO productCreateDTO) {
        Product updatedProduct = productService.updateProduct(productId, productCreateDTO.toEntity());
        if (updatedProduct != null) {
            return ProductResponseDTO.fromEntity(updatedProduct);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
