package com.example.ecommerce_platform.payment_service.controller;

import com.example.ecommerce_platform.payment_service.model.Payment;
import com.example.ecommerce_platform.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Process a payment (simulate a payment gateway call)
    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody Payment payment){
        Payment payment1 = paymentService.processPayment(payment);
        return ResponseEntity.ok(payment1);
    }

    // Retrieve a payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id){
        return paymentService.getPaymentById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Retrieve all payments for a given order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrderId(@PathVariable String orderId) {
        List<Payment> payments = paymentService.getPaymentsByOrderId(orderId);
        return ResponseEntity.ok(payments);
    }
}
