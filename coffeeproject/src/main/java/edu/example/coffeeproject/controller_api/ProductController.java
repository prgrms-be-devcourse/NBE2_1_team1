package edu.example.coffeeproject.controller_api;

import edu.example.coffeeproject.DTO.ProductRequestDTO;
import edu.example.coffeeproject.DTO.ProductResponseDTO;
import edu.example.coffeeproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@Log4j2
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> addProduct(
            @Validated @RequestBody ProductRequestDTO productRequestDTO) {

        // 클라이언트가 보낸 데이터 로그에 출력
        log.info("요청받은 productRequestDTO(상품 데이터) : " + productRequestDTO);

        // 서버가 클라이언트에게 보낸 데이터 로그에 출력
        // 로그 찍어보기 위해서 일부러 productResponseDTO 변수에 저장
        ProductResponseDTO productResponseDTO  = productService.addProduct(productRequestDTO);
        log.info("반환된 productResponseDTO(DB의 상품 데이터) : " + productResponseDTO);

        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(
            @Validated @PathVariable Long productId) {

        log.info("요청받은 productId(상품 아이디) : " + productId);

        ProductResponseDTO productResponseDTO = (productService.readProduct(productId));
        log.info("반환된 productResponseDTO(DB의 상품 데이터) : " + productResponseDTO);

        return ResponseEntity.ok(productResponseDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> modifyProduct(
            @Validated @PathVariable Long productId,
            @RequestBody ProductRequestDTO productRequestDTO) {

        log.info("요청받은 수정 사항 productRequestDTO : " + productRequestDTO);
        log.info("요청받은 수정 productId : " + productId);

        ProductResponseDTO productResponseDTO = productService.modifyProduct(productId, productRequestDTO);

        log.info("수정 완료된 productResponseDTO : " + productResponseDTO);

        return ResponseEntity.ok(productResponseDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@Validated @PathVariable Long productId) {
        ProductResponseDTO productResponseDTO = productService.readProduct(productId);

        if (productResponseDTO != null) {
            productService.removeProduct(productId);
            log.info("삭제된 productResponseDTO : " + productResponseDTO);
            return ResponseEntity.ok(Map.of("delete result", "성공"));
        }

        // 500 에러에 막혀서 해당 결과는 나오지 않음(NoSuchElementException) -> 예외처리 필요
        // Map으로 반환하나 그냥 String으로 반환하나 비슷한 것 같은데??
        return ResponseEntity.ok(Map.of("delete result ", "실패"));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> productResponseDTOList = productService.readAllProducts();
        log.info("DB로부터 반환된 productResponseDTOList" + productResponseDTOList);

        return ResponseEntity.ok(productResponseDTOList);
    }

}


    // 상품 페이지(페이징)
//    @GetMapping("/list")
//    public ResponseEntity<Page<ProductListDTO>> getList(@ModelAttribute @Validated PageRequestDTO pageRequestDTO) {
//        log.info("getList -----" + pageRequestDTO);
//
//        return ResponseEntity.ok(productService.getList(pageRequestDTO));
//    }
