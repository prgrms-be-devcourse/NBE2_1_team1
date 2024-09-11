package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.OrderStatus;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.repository.OrderItemRepository;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity(orderRepository, productRepository, orderItemRepository);

        return new OrderDTO(order);
    }

    public List<OrderDTO> read(String email) {     //email로 주문 목록 조회
        List<Order> orders = orderRepository.findByEmailList(email).get();
        if (!orders.isEmpty()) {
            List<OrderDTO> orderDTOList = new ArrayList<>();

            for (Order order : orders) {
                orderDTOList.add(new OrderDTO(order));
            }

            return orderDTOList;
        } else {
            throw OrderException.NOT_FOUND.get();
        }

    }

    public List<OrderDTO> findAll(){
        return orderRepository.findAll().stream().map(OrderDTO::new).toList();
    }

    public OrderDTO userModify(OrderDTO orderDTO) {
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());
        Order order = foundOrder.orElseThrow(OrderException.NOT_FOUND::get);

        try {
            order.changeAddress(orderDTO.getAddress());
            order.changePostCode(orderDTO.getPostCode());

            orderRepository.save(order);
            return new OrderDTO(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw OrderException.NOT_MODIFIED.get();
        }
    }

    public OrderDTO adminModify(OrderDTO orderDTO) {
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());
        Order order = foundOrder.orElseThrow(OrderException.NOT_FOUND::get);

        try {
            order.changeOrderStatus(orderDTO.getOrderStatus());

            orderRepository.save(order);
            return new OrderDTO(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw OrderException.NOT_MODIFIED.get();
        }
    }

    public void delete(Long orderId){
        orderRepository.deleteById(orderId);
    }


}
