package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.email = :email")
    Optional<List<Order>> findByEmailList(@Param("email") String email);

    @Query("SELECT o FROM Order o WHERE o.orderStatus = 'NO' AND o.orderDate < :twoPM")
    List<Order> findOrdersBeforeTwoPMWithStatusAccepted(@Param("twoPM") LocalDateTime twoPM);
}
