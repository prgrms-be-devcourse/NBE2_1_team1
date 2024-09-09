package edu.example.coffeeproject.Controller;

import edu.example.coffeeproject.DTO.ProductDTO;
import edu.example.coffeeproject.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/v1/products"))
@Log4j2
public class ProductController {

    private final ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ProductDTO> register(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

//    @GetMapping
//    public ResponseEntity<List<ProductDTO>> getProductList() {
//    }

}