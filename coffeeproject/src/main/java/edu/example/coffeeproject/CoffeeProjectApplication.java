package edu.example.coffeeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing        //엔티티 시간 자동 처리 설정
public class CoffeeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeProjectApplication.class, args);
    }

}
