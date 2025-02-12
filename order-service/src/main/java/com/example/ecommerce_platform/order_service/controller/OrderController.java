package com.example.ecommerce_platform.order_service.controller;

import com.example.ecommerce_platform.order_service.dto.OrderStatusUpdateDTO;
import com.example.ecommerce_platform.order_service.model.Order;
import com.example.ecommerce_platform.order_service.model.OrderItem;
import com.example.ecommerce_platform.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    // Get an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all orders for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Update order status (e.g., for cancellation or completion)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,
                                               @Valid @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) {
        // The DTO's status field will be bound directly from the JSON payload
        String sanitizedStatus = orderStatusUpdateDTO.getStatus().trim();
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, sanitizedStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Add an order item to an existing order
    @PostMapping("/{orderId}/items")
    public ResponseEntity<?> addOrderItem(@PathVariable String orderId, @Valid @RequestBody OrderItem orderItem) {
        try {
            Order updatedOrder = orderService.addOrderItem(orderId, orderItem);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Update an existing order item
    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> updateOrderItem(@PathVariable String orderId,
                                             @PathVariable String itemId,
                                             @Valid @RequestBody OrderItem updatedItem) {
        try {
            Order updatedOrder = orderService.updateOrderItem(orderId, itemId, updatedItem);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Remove an order item
    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> removeOrderItem(@PathVariable String orderId, @PathVariable String itemId) {
        try {
            Order updatedOrder = orderService.removeOrderItem(orderId, itemId);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Cancel an order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        try {
            Order cancelledOrder = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(cancelledOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Complete an order
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable String orderId) {
        try {
            Order completedOrder = orderService.completeOrder(orderId);
            return ResponseEntity.ok(completedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

