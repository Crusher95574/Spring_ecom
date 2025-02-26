package com.example.ecommerce_platform.payment_service.repository;

import com.example.ecommerce_platform.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,String> {
    List<Payment> findByOrderId(String orderId);
}
