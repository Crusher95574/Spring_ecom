package com.example.ecommerce_platform.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
public class OrderServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(OrderServiceApplication.class);
    }
}
