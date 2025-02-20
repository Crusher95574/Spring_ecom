package com.example.ecommerce_platform.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class APIGatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(APIGatewayApplication.class,args);
    }
}
