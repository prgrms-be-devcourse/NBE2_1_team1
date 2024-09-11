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

    @NotBlank(message = "값을 입력해주세요.")
    private String address;

    @NotBlank(message = "값을 입력해주세요.")
    private String postcode;

    @NotNull(message = "값을 입력해주세요.")
    private List<OrderItemRequestDTO> orderItems;
}

// @Email -> "@" 기호와 "."이 포함된 형식의 이메일 값만 유효하다고 판단