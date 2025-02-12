package com.example.ecommerce_platform.order_service.repository;

import com.example.ecommerce_platform.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUserId(String userId);
}
