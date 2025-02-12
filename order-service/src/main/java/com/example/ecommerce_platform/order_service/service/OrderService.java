package com.example.ecommerce_platform.order_service.service;

import com.example.ecommerce_platform.order_service.model.Order;
import com.example.ecommerce_platform.order_service.model.OrderItem;
import com.example.ecommerce_platform.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create a new order
    public Order createOrder(Order order) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
        // Calculate total amount based on order items
        recalcTotal(order);
        // Set back-reference for each order item
        order.getOrderItems().forEach(item -> item.setOrder(order));
        return orderRepository.save(order);
    }

    // Retrieve an order by ID
    public Optional<Order> getOrderById(String orderId){
        return orderRepository.findById(orderId);
    }

    // Retrieve all orders for a given user
    public List<Order> getOrdersByUserId(String userId){
        return orderRepository.findByUserId(userId);
    }

    // Update the order status
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Delete an order
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    // Add an order item to an existing order (only if order is in PENDING status)
    public Order addOrderItem(String orderId, OrderItem newItem){
       Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be modified");
        }
        newItem.setOrder(order);
        order.getOrderItems().add(newItem);
        recalcTotal(order);
        return orderRepository.save(order);
    }

    // Update an existing order item in an order (only if order is pending)
    public Order updateOrderItem(String orderId,String itemId,OrderItem updatedItem){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be modified");
        }
        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order item not found with id " + itemId));

        // Update order item details
        orderItem.setProductId(updatedItem.getProductId());
        orderItem.setQuantity(updatedItem.getQuantity());
        orderItem.setPrice(updatedItem.getPrice());
        orderItem.setProductName(updatedItem.getProductName());
        recalcTotal(order);
        return orderRepository.save(order);
    }

    // Remove an order item from an order (only if order is pending)
    public Order removeOrderItem(String orderId, String itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be modified");
        }
        boolean removed = order.getOrderItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new RuntimeException("Order item not found with id " + itemId);
        }
        recalcTotal(order);
        return orderRepository.save(order);
    }

    // Cancel an order (only if order is pending)
    public Order cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }

    // Complete an order (only if order is pending)
    public Order completeOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be completed");
        }
        order.setStatus("COMPLETED");
        return orderRepository.save(order);
    }

    // Helper method to recalculate the total amount based on order items
    private void recalcTotal(Order order) {
        double total = order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);
    }
}
