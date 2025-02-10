package com.example.ecommerce_platform.product_service.repository;

import com.example.ecommerce_platform.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
