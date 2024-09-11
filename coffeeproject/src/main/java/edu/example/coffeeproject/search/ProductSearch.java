package edu.example.coffeeproject.search;

import edu.example.coffeeproject.DTO.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<ProductResponseDTO> list(Pageable pageable);
}
