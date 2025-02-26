package com.example.ecommerce_platform.payment_service.service;

import com.example.ecommerce_platform.payment_service.model.Payment;
import com.example.ecommerce_platform.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    // Create a payment record (simulate processing payment)
    public Payment processPayment(Payment payment){
        if (payment.getAmount() <= 0) {
            payment.setStatus("FAILED");
            payment.setErrorMessage("Invalid amount.");
        } else {
            payment.setStatus("COMPLETED");
        }
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(String id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
