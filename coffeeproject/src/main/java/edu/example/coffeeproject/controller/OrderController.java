package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    //주문 생성 (주문 아이템도 함께 생성됨)
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.register(orderDTO));
    }

    //주문 목록 보기
    @GetMapping("/list")
    public ResponseEntity<List<OrderDTO>> findAllOrder(){
        List<OrderDTO> orders = orderService.findAll().stream().map(OrderDTO::new).toList();
        return ResponseEntity.ok().body(orders);
    }
}
