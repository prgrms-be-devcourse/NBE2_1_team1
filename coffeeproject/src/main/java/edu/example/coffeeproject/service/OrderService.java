package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.exception.OrderNotFoundException;
import edu.example.coffeeproject.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // Order엔티티의 OrderItems가 지연 로딩되는 에러 해결 (LazyInitializationException)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity();
        order.getOrderItems().forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return OrderDTO.fromEntity(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        existingOrder.setEmail(orderDTO.getEmail());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setPostcode(orderDTO.getPostcode());

        existingOrder.getOrderItems().clear(); // 업데이트 오류 해결을 위한 상품 목록 초기화
        // 하나의 주문에 속한 여러 상품을 for문으로 순회하여 상품 하나씩 업데이트
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            OrderItem newItem = new OrderItem();
            newItem.setProductId(itemDTO.getProductId());
            newItem.setCategory(itemDTO.getCategory());
            newItem.setPrice(itemDTO.getPrice());
            newItem.setQuantity(itemDTO.getQuantity());
            newItem.setOrder(existingOrder);
            existingOrder.getOrderItems().add(newItem);
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderDTO.fromEntity(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        orderRepository.deleteById(orderId);
    }
}
