package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Log4j2
@Validated
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("상품이 추가되었습니다. {}", productDTO);
        return productService.saveProduct(productDTO);
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

    @PutMapping("/{productId}")
    public ProductDTO updateProduct(@PathVariable @Min(1) Long productId, @Valid @RequestBody ProductDTO productDTO) {
        log.info("상품번호 {}번을 업데이트합니다. 업데이트 내용: {}", productId, productDTO); // productDTO 인스턴스 참조값 toString 반환
        return productService.updateProduct(productId, productDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable @Min(1) Long productId) {
        log.info("상품번호 {}번을 삭제합니다.", productId);
        productService.deleteProduct(productId);
    }
}
