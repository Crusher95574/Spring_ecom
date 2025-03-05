//package com.example.ecommerce_platform.cart_service;
//
//import com.example.ecommerce_platform.cart_service.model.Cart;
//import com.example.ecommerce_platform.cart_service.model.CartItem;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class CartServiceIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private Cart cart;
//    private CartItem item;
//
//    @BeforeEach
//    public void setUp() {
//        cart = new Cart();
//        cart.setUserId("john123");
//
//        item = new CartItem();
//        item.setProductId("101");
//        item.setProductName("Phone");
//        item.setQuantity(2);
//        item.setPrice(29.99);
//
//        cart.setCartItems(List.of(item));
//    }
//
//    @Test
//    public void testAddItemToCart() {
//        ResponseEntity<Cart> response = restTemplate.postForEntity("/cart", cart, Cart.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP 200 OK");
//        Cart createdCart = response.getBody();
//        assertNotNull(createdCart, "Created cart should not be null");
//        assertNotNull(createdCart.getUserId(), "User ID should be set");
//        assertFalse(createdCart.getCartItems().isEmpty(), "Cart should contain items");
//    }
////
////    @Test
////    public void testGetCartByUserId() {
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        HttpEntity<Cart> request = new HttpEntity<>(cart, headers);
////        // First, add an item to the cart
////        ResponseEntity<Cart> createResponse = restTemplate.postForEntity("/cart", request, Cart.class);
////        assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Cart creation failed");
////        Cart createdCart = createResponse.getBody();
////        assertNotNull(createdCart, "Created cart should not be null");
////        assertNotNull(createdCart.getUserId(), "User ID should be generated");
////
////        // Retrieve the cart using GET request
////        ResponseEntity<Cart> response = restTemplate.getForEntity("/cart/" + createdCart.getUserId(), Cart.class);
////        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP 200 OK");
////        Cart retrievedCart = response.getBody();
////        assertNotNull(retrievedCart, "Retrieved cart should not be null");
////        assertEquals(createdCart.getUserId(), retrievedCart.getUserId(), "User ID should match");
////        assertFalse(retrievedCart.getCartItems().isEmpty(), "Cart should contain items");
////    }
//}
