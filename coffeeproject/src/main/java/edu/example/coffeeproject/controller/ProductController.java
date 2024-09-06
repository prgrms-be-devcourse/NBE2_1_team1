package edu.example.coffeeproject.controller;


import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public List<Product> getAllProducts() {
        List<Product> productList = testService.getAllProducts();
        return productList;
    }
}