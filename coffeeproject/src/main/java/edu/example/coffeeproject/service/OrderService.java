package edu.example.coffeeproject.service;

import edu.example.coffeeproject.DTO.request.OrderRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderResponseDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.mapper.OrderItemMapper;
import edu.example.coffeeproject.mapper.OrderMapper;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 주문 등록
    public OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO) {
        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
                .map(orderItemRequestDTO -> {
                    Optional<Product> product = productRepository.findById(orderItemRequestDTO.getProductId());
                    Product getProduct = product.get(); //  Optional 벗기는 것은 나중에 예외처리로 하기
                    return OrderItemMapper.toOrderItemEntity(orderItemRequestDTO, getProduct);
                }).collect(Collectors.toList());

        Order order = OrderMapper.toOrderEntity(orderRequestDTO, orderItems);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        // 각 OrderItem에 Order 필드 지정
        // orderitem에 setter 어노테이션 추가

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toOrderResponseDTO(savedOrder);
    }

    // 주문 조회
    public OrderResponseDTO readOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order getOrder = order.get();
        return OrderMapper.toOrderResponseDTO(getOrder);
    }

    // 주문 변경
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {

        Optional<Order> order = orderRepository.findById(orderId);
        Order getOrder = order.get();

        if (orderRequestDTO.getEmail() != null) {
            getOrder.changeEmail(orderRequestDTO.getEmail());
        }

        if (orderRequestDTO.getAddress() != null) {
            getOrder.changeAddress(orderRequestDTO.getAddress());
        }

        if (orderRequestDTO.getPostcode() != null) {
            getOrder.changePostcode(orderRequestDTO.getPostcode());
        }

        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
                .map(orderItemRequestDTO -> {
                    Optional<Product> product = productRepository.findById(orderItemRequestDTO.getProductId());
                    Product getProduct = product.get();

                    return OrderItemMapper.toOrderItemEntity(orderItemRequestDTO, getProduct);
                }).collect(Collectors.toList());

        getOrder.getOrderItems().clear();
        getOrder.getOrderItems().addAll(orderItems);

        Order updatedOrder = orderRepository.save(getOrder);
        return OrderMapper.toOrderResponseDTO(updatedOrder);
    }

    // 주문 삭제
    public void deleteOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
//                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        orderRepository.delete(order.get());
    }


    // 전체 주문 조회
    public List<OrderResponseDTO> readAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    // 주문 페이징

}