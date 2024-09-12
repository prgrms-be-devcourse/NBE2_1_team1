package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@AllArgsConstructor
public class OrderDTO {
    @NotEmpty
    private String email;

    private String address;
    private String postcode;

    private List<OrderItemDTO>  items;
    private OrderStatus orderStatus;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;



    public OrderDTO(Order order){
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.orderStatus = order.getOrderStatus();
        this.createDate = order.getCreateDate();
        this.updateDate = order.getUpdateDate();

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productId(orderItem.getProduct().getProductId())
                    .category(orderItem.getProduct().getCategory())
                    .price(orderItem.getProduct().getPrice())
                    .quantity(orderItem.getQuantity())
                    .build();

            orderItemDTOS.add(orderItemDTO);
        }
        this.items = orderItemDTOS;
    }
}
