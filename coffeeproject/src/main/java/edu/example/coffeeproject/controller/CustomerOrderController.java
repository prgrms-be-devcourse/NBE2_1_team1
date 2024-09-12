package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.service.OrderService;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Log4j2
@Validated
public class CustomerOrderController {

    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("주문이 생성되었습니다. {}", orderDTO);
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable @Min(1) Long orderId) {
        log.info("주문번호 {}번을 조회합니다.", orderId);
        return orderService.getOrderById(orderId);
    }
}