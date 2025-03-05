package com.example.ecommerce_platform.order_service;

import com.example.ecommerce_platform.order_service.model.Order;
import com.example.ecommerce_platform.order_service.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Order order;
    private OrderItem item;

    @BeforeEach
    public void setUp(){
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
    public void testCreateOrder() {
        // POST request to create order
        ResponseEntity<Order> response = restTemplate.postForEntity("/orders", order, Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order createdOrder = response.getBody();
        assertNotNull(createdOrder);
        assertNotNull(createdOrder.getId());
    }

    @Test
    public void testGetOrderById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Order> request = new HttpEntity<>(order, headers);

        // Send a POST request to create the order
        ResponseEntity<Order> createResponse = restTemplate.postForEntity("/orders", request, Order.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Order creation failed");
        Order createdOrder = createResponse.getBody();
        assertNotNull(createdOrder, "Created order should not be null");
        assertNotNull(createdOrder.getId(), "Order ID should be generated");

        // Properly construct the URL by adding a slash between "/orders" and the order ID.
        ResponseEntity<Order> response = restTemplate.getForEntity("/orders/" + createdOrder.getId(), Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order retrievedOrder = response.getBody();
        assertNotNull(retrievedOrder);
        assertEquals(createdOrder.getId(), retrievedOrder.getId());
    }
}
