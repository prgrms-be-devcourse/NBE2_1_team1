package edu.example.coffeeproject.controller_api;

import edu.example.coffeeproject.DTO.PageRequestDTO;
import edu.example.coffeeproject.DTO.request.ProductRequestDTO;
import edu.example.coffeeproject.DTO.response.ProductResponseDTO;
import edu.example.coffeeproject.repository.ProductRepository;
import edu.example.coffeeproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
            @Validated @RequestBody ProductRequestDTO productRequestDTO) {

        log.info("요청받은 수정 사항 productRequestDTO : " + productRequestDTO);
        log.info("요청받은 수정 productId : " + productId);

        ProductResponseDTO productResponseDTO = productService.modifyProduct(productId, productRequestDTO);

        log.info("수정 완료된 productResponseDTO : " + productResponseDTO);

        return ResponseEntity.ok(productResponseDTO);
    }

    // 이슈 : orderItem에 사용되고 있는 product는 참조하고 있기 때문에 삭제가 안됨
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@Validated @PathVariable Long productId) {

        // 삭제 요청을 받은 product의 정보 log로 찍기 위해서 productService.readProduct 메서드 사용
        ProductResponseDTO foundProduct = productService.readProduct(productId);
        log.info("요청받은 삭제 Product : " + foundProduct);

        // 상품 삭제 처리 후, 로그 출력
        productService.removeProduct(productId);
        log.info("삭제된 productResponseDTO : " + foundProduct);

        // 현재 시간 변수 now에 저장
        LocalDateTime now = LocalDateTime.now();

        // Map의 value에 여러가지 타입을 담기 위해 해당 클래스의  value 타입을 Object로 변경
        // Map으로 다양한 값으 전달해보기 위해서 삭제된 productId,  삭제된 날짜를 같이 반환
        return ResponseEntity.ok(Map.of(
                "delete result", "성공",
                "deleted ProductId : ", productId,
                "deleted Time : ", now));
        }

    @GetMapping("/AllList")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> productResponseDTOList = productService.readAllProducts();
        log.info("DB로부터 반환된 productResponseDTOList" + productResponseDTOList);

        return ResponseEntity.ok(productResponseDTOList);
    }

    // 브라우저로부터 받는게 없는데, PageRequestDTO 얘를 매개변수로 받는게 무슨 뜻인지??
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getPageProducts(@ModelAttribute PageRequestDTO pageRequestDTO) {

        Page<ProductResponseDTO> productResponseDTOPage = productService.getPageList(pageRequestDTO);
        log.info("DB로부터 반환된 productResponseDTOList" + productResponseDTOPage);

        return ResponseEntity.ok(productResponseDTOPage);
    }

}