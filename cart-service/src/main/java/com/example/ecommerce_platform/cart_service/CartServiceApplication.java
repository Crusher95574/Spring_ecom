package com.example.ecommerce_platform.cart_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CartServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(CartServiceApplication.class);
    }
}