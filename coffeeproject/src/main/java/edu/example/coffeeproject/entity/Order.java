package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="tbl_order")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    private String address;
    private String postCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @LastModifiedDate
    private LocalDateTime orderDate;


    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostCode(String postCode) {
        this.postCode = postCode;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
