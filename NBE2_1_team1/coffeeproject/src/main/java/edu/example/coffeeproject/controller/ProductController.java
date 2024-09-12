package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.PageRequestDTO;
import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@Log4j2
@Tag(name = "Product", description = "상품 관련 API")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품 정보를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
    })
    @PostMapping
    public ResponseEntity<ProductDTO> register(@Validated @RequestBody ProductDTO productDTO) {

        log.info("--- register()");
        log.info("productDTO: " + productDTO);

        return ResponseEntity.ok(productService.register(productDTO));  //상태 코드를 200 OK로 하여, 상품 등록 서비스가 반환하는 데이터를 뷰로 전달
    }

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공"),
    })
    @Parameter(name = "pageRequestDTO", description = "조회를 원하는 페이지와 페이지당 게시물 수")
    @GetMapping  //상품 목록 조회
    public ResponseEntity<Page<ProductDTO>> getList(@Validated PageRequestDTO pageRequestDTO) {
        log.info("getList -----" + pageRequestDTO);

        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }

    @Operation(summary = "상품 정보 수정", description = "상품 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 정보 수정 성공"),
    })
    @Parameter(name = "productId", description = "조회를 원하는 상품 번호")
    @PutMapping({"/{productId}"})   //상품 정보 수정
    public ResponseEntity<ProductDTO> modify(@RequestBody ProductDTO productDTO, @PathVariable("productId") Long productId) {
        log.info("modify() -----" + productDTO);

        if(!productId.equals(productDTO.getProductId())) { //존재하지 않는 productId인 경우
            throw ProductException.NOT_FOUND.get();
        }


        return ResponseEntity.ok(productService.modify(productDTO));
    }

    @Operation(summary = "상품 정보 삭제", description = "상품 정보를 삭제합니다.")
    @Parameter(name = "productId", description = "삭제를 원하는 상품 번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 정보 삭제 성공"),
    })
    @DeleteMapping("/{productId}")  //삭제
    public ResponseEntity<Map<String, String>> remove(@PathVariable("productId") Long productId) {
        log.info("--- remove()");
        log.info("--- productId : " + productId);


        productService.remove(productId);    //삭제 처리 후
        Map<String, String> result = Map.of("result", "success");   //Map에 키는 result, 값은 success를 저장
        return ResponseEntity.ok(result);
    }
}
