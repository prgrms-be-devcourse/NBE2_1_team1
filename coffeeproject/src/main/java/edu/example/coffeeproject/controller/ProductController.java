package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.PageRequestDTO;
import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.dto.ProductListDTO;
import edu.example.coffeeproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@Log4j2
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> register(@Validated @RequestBody ProductDTO productDTO) {

        log.info("--- register()");
        log.info("productDTO: " + productDTO);

        return ResponseEntity.ok(productService.register(productDTO));  //상태 코드를 200 OK로 하여, 상품 등록 서비스가 반환하는 데이터를 뷰로 전달
    }

    @GetMapping
    public ResponseEntity<Page<ProductListDTO>> getList(@Validated PageRequestDTO pageRequestDTO) {
        log.info("getList -----" + pageRequestDTO);

        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }
}
