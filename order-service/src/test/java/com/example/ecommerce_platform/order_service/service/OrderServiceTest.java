package com.example.ecommerce_platform.order_service.service;

import com.example.ecommerce_platform.order_service.model.Order;
import com.example.ecommerce_platform.order_service.model.OrderItem;
import com.example.ecommerce_platform.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService; // Contains logic for creating orders

    private Order order;
    private OrderItem item;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setUserId("john123");
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        item = new OrderItem();
        item.setProductId("101");
        item.setProductName("Phone");
        item.setQuantity(2);
        item.setPrice(29.99);
        order.setOrderItems(List.of(item));
    }

    @Test
    public void testCreateOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId("generate-id");
            return savedOrder;
        });

        // Act: Create order via service
        Order createdOrder = orderService.createOrder(order);

        // Assert: Verify order creation
        assertNotNull(createdOrder.getId());
        assertEquals("PENDING", createdOrder.getStatus());
    }
}
