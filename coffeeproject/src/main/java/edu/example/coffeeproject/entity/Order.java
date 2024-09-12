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
    @Column(columnDefinition = "ENUM('ACCEPTED','COMPLETED') DEFAULT 'ACCEPTED'")
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    /**
     * mappedBy : OrderItems 테이블에 orderId 외래키 컬럼이 존재함을 명시
     * cascade = CascadeType.ALL : Order엔티티에 대한 모든 데이터베이스 작업이 OrderItem 엔티티에도 자동으로 적용
     * orphanRemoval = true : Order에서 제거된 OrderItem을 데이터베이스에서도 자동으로 삭제
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Order엔티티를 DB에 저장시 OrderStatus 디폴트 값을 ACCEPTED로 보장
    @PrePersist
    public void prePersist() {
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.ACCEPTED;
        }
    }
}
