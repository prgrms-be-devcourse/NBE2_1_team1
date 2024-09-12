package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 주문_정보_등록() {
        Long productId = 2L;
        String email = "bbb@naver.com";
        int quantity = 3;

        Optional<Order> foundOrder = orderRepository.findByEmail(email);
        Order savedOrder = foundOrder.orElseGet(() -> {
           Order newOrder = Order.builder()
                   .email(email)
                   .address("인천1")
                   .postCode("1234")
                   .orderStatus(OrderStatus.YES)
                   .build();
           return orderRepository.save(newOrder);
        });
        Product product = Product.builder().productId(productId).price(6000).build();

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(savedOrder)
                .category(Category.COFFEE_BEAN_PACKAGE)
                .price(product.getPrice())
                .quantity(quantity)
                .build();
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        assertNotNull(savedOrderItem);
    }

    @Test
    @Transactional
    @Commit
    public void 주문_수정() {


    }

}
