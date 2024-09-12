package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query(" SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.email = :email ")
    Optional<List<Order>> findByEmailList(@Param("email") String email);
    // 쿼리 설명 필요
}
