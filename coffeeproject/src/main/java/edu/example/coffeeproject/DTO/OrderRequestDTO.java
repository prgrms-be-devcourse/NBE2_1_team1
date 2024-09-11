package edu.example.coffeeproject.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDTO {
    private String email;
    private String address;
    private String postcode;
    private List<OrderRequestDTO> orderItems;
}