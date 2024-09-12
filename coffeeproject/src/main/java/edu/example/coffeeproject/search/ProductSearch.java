package edu.example.coffeeproject.search;

import edu.example.coffeeproject.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<ProductDTO> list(Pageable pageable);
}
