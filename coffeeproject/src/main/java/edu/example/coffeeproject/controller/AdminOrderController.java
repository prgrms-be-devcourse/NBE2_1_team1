package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.service.OrderService;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Log4j2
@Validated
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public Page<OrderDTO> getAllOrders(
            @PageableDefault(sort = "orderId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("모든 주문을 페이지별로 조회합니다. 페이지: {}, 크기: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable @Min(1) Long orderId) {
        log.info("주문번호 {}번을 조회합니다.", orderId);
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable @Min(1) Long orderId, @RequestBody OrderDTO orderDTO) {
        log.info("주문번호 {}번을 업데이트합니다. 업데이트 내용: {}", orderId, orderDTO); // orderDTO 인스턴스 참조값 toString 반환
        return orderService.updateOrder(orderId, orderDTO);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable @Min(1) Long orderId) {
        log.info("주문번호 {}번을 삭제합니다.", orderId);
        orderService.deleteOrder(orderId);
    }
}