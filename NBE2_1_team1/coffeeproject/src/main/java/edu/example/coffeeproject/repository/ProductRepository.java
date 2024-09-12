package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch{

}
