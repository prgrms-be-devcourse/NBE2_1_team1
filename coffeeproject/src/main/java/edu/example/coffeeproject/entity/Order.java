package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String postcode;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACCEPTED','CANCELLED','PAYMENT_CONFIRMED','PENDING','READY_FOR_DELIVERY','SETTLED','SHIPPED') DEFAULT 'PENDING'")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    // mappedBy를 사용하여 OrderItems 테이블에 orderId 외래키 컬럼이 존재함을 명시
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Order엔티티를 DB에 저장시 OrderStatus 디폴트 값을 PENDING으로 보장
    @PrePersist
    public void prePersist() {
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.PENDING;
        }
    }
}
