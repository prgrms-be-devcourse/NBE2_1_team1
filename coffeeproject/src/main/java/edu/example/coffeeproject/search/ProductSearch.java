package edu.example.coffeeproject.search;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.dto.ProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<ProductListDTO> list(Pageable pageable);
}
