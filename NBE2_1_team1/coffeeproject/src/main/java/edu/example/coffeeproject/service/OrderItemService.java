package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.repository.OrderItemRepository;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public void modifyOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemDTO.getOrderItemId())
                .orElseThrow(OrderException.ORDER_ITEM_NOT_FOUND::get);
        try {
            orderItem.changeQuantity(orderItemDTO.getQuantity());
            if (orderItem.getQuantity() <= 0) {
                try {
                    orderItemRepository.delete(orderItem);
                    return;
                } catch(Exception e) {
                    log.error("--- error : " + e.getMessage());
                    throw OrderException.FAIL_MODIFY.get();
                }
            }
        } catch (Exception e) {
            log.error("--- error : " + e.getMessage());
            throw OrderException.FAIL_MODIFY.get();
        }
    }

}
