package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.service.OrderItemService;
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
    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderDTO> register(@RequestBody OrderDTO orderDTO) {
        log.info("--- register()");
        log.info("orderDTO: " + orderDTO);

        return ResponseEntity.ok(orderService.addOrder(orderDTO));
    }

    @GetMapping("/{email}")  //이메일로 목록 조회
    public ResponseEntity<List<OrderDTO>> orderListByEmail(@PathVariable String email) {
        log.info("--- OrderListByEmail()");
        log.info("--- email: " + email);


        return ResponseEntity.ok(orderService.read(email));
    }

    @GetMapping("/list")   //전체 주문 목록 조회
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping("/{orderId}")  //주문 정보 수정(사용자)
    public ResponseEntity<OrderDTO> userModify(@PathVariable Long orderId,
                                                @RequestBody OrderDTO orderDTO) {
        log.info("--- userModify()");
        log.info("--- orderId: " + orderId);

        return ResponseEntity.ok(orderService.userModify(orderDTO));
    }

    @PutMapping("/orderStatus/{orderId}")    //주문 처리 상태 수정(관리자)
    public ResponseEntity<OrderDTO> adminModify(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        log.info("--- adminModify()");
        log.info("--- orderId: " + orderId);

        return ResponseEntity.ok(orderService.adminModify(orderDTO));
    }

    @PutMapping("/quantity/{orderItemId}")
    public ResponseEntity<Map<String, String>> modifyQuantity(@PathVariable Long orderItemId, @RequestBody OrderItemDTO orderItemDTO) {
        log.info("--- modifyQuantity()");
        log.info("--- orderItemId: " + orderItemId);
        orderItemService.modifyOrderItem(orderItemDTO);

        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> Remove(@PathVariable Long orderId) {
        orderService.delete(orderId);

        return ResponseEntity.ok(Map.of("result", "success"));
    }




}
