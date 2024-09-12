package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.orderStatus = 'Y' WHERE o.createDate < :endDateTime")
    void updateOrderStatus(@Param("endDateTime") LocalDateTime endDateTime);

    List<Order> findByEmail(String email);
}
