package edu.example.coffeeproject.service;

import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void initializeProducts() {
        if (productRepository.count() == 0) {  // 데이터가 없을 때만 삽입
            productRepository.save(new Product(null, "Columbia Coffee", 10000, "콜롬비아의 맛있는 커피", null, null));
            productRepository.save(new Product(null, "Columbia Quindio", 20000, "콜롬비아산 Quindio 커피", null, null));
            productRepository.save(new Product(null, "Brazil Serra Do Coffee", 15000, "브라질산 풍미가득한 커피", null, null));
            productRepository.save(new Product(null, "Ethiopia Sidamo", 8000, "에디오피아 커피 빈", null, null));
        }
    }
}