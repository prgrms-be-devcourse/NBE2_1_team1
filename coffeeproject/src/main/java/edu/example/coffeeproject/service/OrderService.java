package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.OrderNotFoundException;
import edu.example.coffeeproject.exception.ProductNotFoundException;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // Order엔티티의 OrderItems가 지연 로딩되는 에러 해결 (LazyInitializationException)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity();
        for (OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));
            item.setCategory(product.getCategory());
            item.setPrice(product.getPrice());
            item.setOrder(order);
        }
        Order savedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(savedOrder);
    }

    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(OrderDTO::fromEntity);
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

        // 업데이트 오류 해결을 위한 상품 목록 초기화
        existingOrder.getOrderItems().clear();
        // 하나의 주문에 속한 모든 상품을 순회하여 상품 하나씩 업데이트
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            OrderItem newItem = itemDTO.toEntity();
            Product product = productRepository.findById(newItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(newItem.getProductId()));
            newItem.setCategory(product.getCategory());
            newItem.setPrice(product.getPrice());
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
