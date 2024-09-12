package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.service.ProductService;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Log4j2
@Validated
public class CustomerProductController {

    private final ProductService productService;

    public CustomerProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public Page<ProductDTO> getAllProducts(
            @PageableDefault(sort = "productId", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("모든 상품을 페이지별로 조회합니다. 페이지: {}, 크기: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable @Min(1) Long productId) {
        log.info("상품번호 {}번을 조회합니다.", productId);
        return productService.getProductById(productId);
    }
}
