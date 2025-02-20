package com.example.ecommerce_platform.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route to User Service via Eureka
                .route("user_service_route", r -> r.path("/users/**")
                        .uri("lb://user-service"))
                // Route to Product Service via Eureka
                .route("product_service_route", r -> r.path("/products/**")
                        .uri("lb://product-service"))
                // Route to Order Service via Eureka
                .route("order_service_route", r -> r.path("/orders/**")
                        .uri("lb://order-service"))
                // Route to Cart Service via Eureka
                .route("cart_service_route", r -> r.path("/cart/**")
                        .uri("lb://cart-service"))
                // Route to Notification Service via Eureka
                .route("notification_service_route", r -> r.path("/notifications/**")
                        .uri("lb://notification-service"))
                .build();
    }
}
