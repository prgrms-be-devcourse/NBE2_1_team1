package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.entity.enums.OrderStatus;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.repository.OrderItemRepository;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    //주문 생성
    public OrderDTO register(OrderDTO orderDTO){

        Order order = Order.builder()
                .email(orderDTO.getEmail())
                .address(orderDTO.getAddress())
                .postcode(orderDTO.getPostcode())
                .orderStatus(OrderStatus.valueOf("N"))
                .build();

        //Order 생성
        orderRepository.save(order);



        //OrderItem 리스트
        List<OrderItem> itemList = new ArrayList<>();

        for(OrderItemDTO itemDTO : orderDTO.getItems()){
            //ProductId로 Product 찾기
            Product foundProduct = productRepository.findById(itemDTO.getProductId())
                                        .orElseThrow(ProductException.NOT_FOUND::get);

            OrderItem orderItem = OrderItem.builder()
                                            .product(foundProduct)
                                            .category(foundProduct.getCategory())
                                            .price(foundProduct.getPrice())
                                            .quantity(itemDTO.getQuantity())
                                            .order(order)
                                            .build();

            //OrderItem 생성
            orderItemRepository.save(orderItem);

            itemList.add(orderItem);
        }
        order.setOrderItems(itemList);
        return new OrderDTO(order);
    }

    //전체 주문 확인
    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    //email로 주문 확인
    public List<Order> findEmail(String email){
        return orderRepository.findByEmail(email);
    }

    //배송 완료 상태 변경
    //cron - "초 분 시 일 월 요일"
    @Scheduled(cron = "0 00 14 * * 1-5", zone = "Asia/Seoul")
    public void updateStatus(){
        LocalDateTime endDateTime = LocalDateTime.now().withHour(14).withMinute(0).withSecond(0).withNano(0);
        orderRepository.updateOrderStatus(endDateTime);
    }

    public void delete(Long orderId){ orderRepository.deleteById(orderId);}
}
