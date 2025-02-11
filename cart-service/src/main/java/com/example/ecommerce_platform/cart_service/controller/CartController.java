package com.example.ecommerce_platform.cart_service.controller;

import com.example.ecommerce_platform.cart_service.model.Cart;
import com.example.ecommerce_platform.cart_service.model.CartItem;
import com.example.ecommerce_platform.cart_service.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get the cart for a given user (userId passed as a path variable)
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId){
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(cart);
    }

    // Add an item to the cart for a given user
    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable String userId,@Valid @RequestBody CartItem item){
        Cart cart = cartService.addNewItemToCart(userId, item);
        return ResponseEntity.ok(cart);
    }

    // Update an existing cart item for a given user
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable String userId,
                                        @PathVariable String itemId,
                                        @Valid @RequestBody CartItem updatedItem) {
        // You might want to check if the cart exists first
        if (cartService.findCartByUserId(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart not found for user: " + userId);
        }
        Cart cart = cartService.updateCartItem(userId, itemId, updatedItem);
        return ResponseEntity.ok(cart);
    }

    // Remove an item from the cart for a given user
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable String userId, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(userId, itemId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart not found for user: " + userId));
    }

    // Clear the entire cart for a given user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        return cartService.clearCart(userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart not found for user: " + userId));
    }
}
