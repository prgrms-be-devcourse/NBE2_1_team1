package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.service.OrderItemService;
import edu.example.coffeeproject.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@Log4j2
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Operation(summary = "주문 정보 등록", description = "주문 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<OrderDTO> register(@Validated @RequestBody OrderDTO orderDTO) {  //주문 상품, 주문 정보 등록
        log.info("--- register()");
        log.info("orderDTO: " + orderDTO);

        return ResponseEntity.ok(orderService.addOrder(orderDTO));
    }

    @Operation(summary = "주문 정보 조회", description = "이메일로 주문 정보를 조회합니다.")
    @Parameter(name = "email", description = "주문조회를 원하는 email 입력")
    @GetMapping("/{email}")  //이메일로 목록 조회
    public ResponseEntity<List<OrderDTO>> orderListByEmail(@PathVariable String email) {
        log.info("--- OrderListByEmail()");
        log.info("--- email: " + email);


        return ResponseEntity.ok(orderService.read(email));
    }

    @Operation(summary = "주문 목록 조회", description = "전체 주문 목록을 조회합니다.")
    @GetMapping("/list")   //전체 주문 목록 조회
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @Operation(summary = "주문 정보 수정(사용자)", description = "주문번호로 주문 정보를 수정합니다.")
    @Parameter(name = "orderId", description = "수정을 원하는 주문 번호")
    @PutMapping("/{orderId}")  //주문 정보 수정(사용자)
    public ResponseEntity<OrderDTO> userModify(@PathVariable Long orderId,
                                                @RequestBody OrderDTO orderDTO) {
        log.info("--- userModify()");
        log.info("--- orderId: " + orderId);

        if(!orderId.equals(orderDTO.getOrderId())) { //존재하지 않는 productId인 경우
            throw OrderException.NOT_FOUND.get();
        }

        return ResponseEntity.ok(orderService.userModify(orderDTO));
    }

    @Operation(summary = "주문 상태 수정(관리자)", description = "주문번호로 주문 상태를 수정합니다.")
    @Parameter(name = "orderId", description = "수정을 원하는 주문 번호")
    @PutMapping("/orderStatus/{orderId}")    //주문 처리 상태 수정(관리자)
    public ResponseEntity<OrderDTO> adminModify(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        log.info("--- adminModify()");
        log.info("--- orderId: " + orderId);

        return ResponseEntity.ok(orderService.adminModify(orderDTO));
    }

    @Operation(summary = "주문 상품 수량 수정", description = "주문 상품 수량을 수정합니다.")
    @Parameter(name = "orderItemId", description = "수정를 원하는 주문 상품 번호")
    @PutMapping("/quantity/{orderItemId}")
    public ResponseEntity<Map<String, String>> modifyQuantity(@PathVariable Long orderItemId, @RequestBody OrderItemDTO orderItemDTO) {
        log.info("--- modifyQuantity()");
        log.info("--- orderItemId: " + orderItemId);
        orderItemService.modifyOrderItem(orderItemDTO);

        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @Operation(summary = "주문 취소", description = "주문 번호로 주문을 삭제합니다.")
    @Parameter(name = "orderId", description = "삭제를 원하는 주문 번호")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> Remove(@PathVariable Long orderId) {
        orderService.delete(orderId);

        return ResponseEntity.ok(Map.of("result", "success"));
    }


}
