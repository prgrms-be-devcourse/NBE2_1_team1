package edu.example.coffeeproject.controller_api;

import edu.example.coffeeproject.DTO.request.OrderRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderResponseDTO;
import edu.example.coffeeproject.service.OrderService;
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
public class OrderController {

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@Validated @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok(orderService.addOrder(orderRequestDTO));
    }

    // 주문 조회
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderResponseDTO>> getOrder(@Validated @PathVariable String email) {
        return ResponseEntity.ok(orderService.readOrder(email));
    }

    // 주문 변경
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Long orderId,
            @Validated @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderRequestDTO));
    }

    // 주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOrder(@Validated @PathVariable Long orderId) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.ok(Map.of("delete result ", "성공"));
    }

    // 전체 주문 조회
    @GetMapping("/List")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.readAllOrders());
    }

    // 주문 페이징

}