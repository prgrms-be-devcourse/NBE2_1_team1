package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderStatus;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.repository.OrderItemRepository;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Scheduled(cron = "0 0 14 * * *")  // 매일 오후 2시에 실행
    public void updateOrderStatus() {
        // 오후 2시 이전에 접수된 ACCEPTED 상태의 주문을 찾는다
        LocalDateTime twoPM = LocalDateTime.now().with(LocalTime.of(14, 0));
        List<Order> orders = orderRepository.findOrdersBeforeTwoPMWithStatusAccepted(twoPM);

        // 상태를 YES로 변경
        for (Order order : orders) {
            order.changeOrderStatus(OrderStatus.YES);
        }

        System.out.println("Updated order status to YES for " + orders.size() + " orders.");
    }


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

    public OrderDTO userModify(OrderDTO orderDTO) { //사용자가 주문 정보를 수정할 때 사용하는 기능(주소, 우편번호 수정 가능)
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());
        Order order = foundOrder.orElseThrow(OrderException.NOT_FOUND::get);

        try {
            order.changeAddress(orderDTO.getAddress());
            order.changePostCode(orderDTO.getPostCode());
            order.changeOrderStatus(OrderStatus.NO);  //사용자가 주문 정보를 변경했으므로 주문상태 초기화

            orderRepository.save(order);
            return new OrderDTO(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw OrderException.NOT_MODIFIED.get();
        }
    }

    public OrderDTO adminModify(OrderDTO orderDTO) {   //관리자가 임의로 주문 상태를 변경해야할 때 사용하는 기능
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
