package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.dto.UpdateProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> findAllProducts(){
        List<ProductDTO> products = productService.findAll().stream().map(ProductDTO::new).toList();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProduct(@PathVariable("id") Long productId){
        Product product = productService.findById(productId);

        return ResponseEntity.ok().body(new ProductDTO(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId){
        productService.delete(productId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId,
                                                 @RequestBody UpdateProductDTO updateProductDTO) {
        Product updatedProduct = productService.update(productId, updateProductDTO);

        return ResponseEntity.ok().body(updatedProduct);
    }
}
