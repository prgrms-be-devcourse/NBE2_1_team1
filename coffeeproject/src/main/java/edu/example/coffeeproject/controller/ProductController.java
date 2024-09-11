package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.PageRequestDTO;
import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.dto.ProductListDTO;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping  //상품 목록 조회
    public ResponseEntity<Page<ProductListDTO>> getList(@Validated PageRequestDTO pageRequestDTO) {
        log.info("getList -----" + pageRequestDTO);

        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }

    @PutMapping({"/{productId}"})   //상품 정보 수정
    public ResponseEntity<ProductDTO> modify(@Validated @RequestBody ProductDTO productDTO, @PathVariable("productId") Long productId) {
        log.info("modify() -----" + productDTO);

        if(!productId.equals(productDTO.getProductId())) { //존재하지 않는 productId인 경우
            throw ProductException.NOT_FOUND.get();
        }


        return ResponseEntity.ok(productService.modify(productDTO));
    }

    @DeleteMapping("/{productId}")  //삭제
    public ResponseEntity<Map<String, String>> remove(@PathVariable("productId") Long productId) {
        log.info("--- remove()");
        log.info("--- productId : " + productId);


        productService.remove(productId);    //삭제 처리 후
        Map<String, String> result = Map.of("result", "success");   //Map에 키는 result, 값은 success를 저장
        return ResponseEntity.ok(result);
    }
}
