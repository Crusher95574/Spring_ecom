package com.example.ecommerce_platform.cart_service.repository;

import com.example.ecommerce_platform.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
    // Find a cart by the associated user identifier
    Optional<Cart> findByUserId(String userId);
}
