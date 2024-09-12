package edu.example.coffeeproject.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDTO {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "우편 번호를 입력해주세요.")
    private String postcode;

    @NotNull(message = "상품을 선택(입력)해주세요.")
    private List<OrderItemRequestDTO> orderItems;
}