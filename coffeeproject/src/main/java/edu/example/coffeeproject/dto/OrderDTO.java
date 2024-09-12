package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.OrderStatus;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.repository.OrderItemRepository;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Schema(description = "주문 Id")
    private Long orderId;
    @Schema(description = "주문자 이메일", example = "aaa.naver.com")
    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    private String email;
    @Schema(description = "배송지")
    @NotBlank(message = "주소는 비어있을 수없습니다..")
    private String address;
    @Schema(description = "우편 번호")
    @NotBlank(message = "우편번호는 비어있을 수 없습니다.")
    private String postCode;
    @Schema(description = "주문 상품")
    @NotEmpty(message = "주문 상품은 비어있을 수 없습니다.")
    private List<OrderItemDTO> orderItems;
    private OrderStatus orderStatus;

    public OrderDTO(Order order) {
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.orderStatus = order.getOrderStatus();

        List<OrderItemDTO> DTOItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productId(orderItem.getProduct().getProductId())
                    .category(orderItem.getProduct().getCategory())
                    .price(orderItem.getProduct().getPrice())
                    .quantity(orderItem.getQuantity())
                    .build();

            DTOItems.add(orderItemDTO);
        }
        this.orderItems = DTOItems;
    }

    public Order toEntity(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        Order order = Order.builder()
                .email(email)
                .address(address)
                .postCode(postCode)
                .orderStatus(OrderStatus.NO)
                .build();

        orderRepository.save(order);

        List<OrderItem> itemList = new ArrayList<>();
        for(OrderItemDTO orderItemDTO : orderItems) {
            Product foundProduct = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(ProductException.NOT_FOUND::get);

            OrderItem orderItem = OrderItem.builder()
                    .product(foundProduct)
                    .category(foundProduct.getCategory())
                    .price(foundProduct.getPrice())
                    .quantity(orderItemDTO.getQuantity())
                    .order(order)
                    .build();

            orderItemRepository.save(orderItem);

            itemList.add(orderItem);
        }

        order.changeOrderItems(itemList);
        return order;
    }

}
