package com.example.ecommerce_platform.cart_service.service;

import com.example.ecommerce_platform.cart_service.model.Cart;
import com.example.ecommerce_platform.cart_service.model.CartItem;
import com.example.ecommerce_platform.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Get the cart for a specific user; if none exists, create one
    public Cart getOrCreateCart(String userId){
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        return cart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
    }

    // Add an item to the user's cart
    // In your CartService.java
    public Cart addNewItemToCart(String userId, CartItem newItem) {
        // Retrieve the cart (create one if it doesn't exist)
        Cart cart = getOrCreateCart(userId);

        // Check if an item with the same productId already exists
        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();

            // Compare other details (for example, productName and price)
            if (!existingItem.getProductName().equals(newItem.getProductName()) ||
                    !existingItem.getPrice().equals(newItem.getPrice())) {
                // If any non-quantity detail is changed, throw an error.
                throw new IllegalArgumentException("Cannot change product details for an existing cart item. Only quantity update is allowed.");
            }

            // If details match, update the quantity only
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
        } else {
            // No matching item found: add the new item to the cart
            cart.addItem(newItem);
        }

        // Save and return the updated cart
        return cartRepository.save(cart);
    }


    // Update a cart item by its ID
    public Cart updateCartItem(String userId,String itemId,CartItem updatedItem){
        Cart cart = getOrCreateCart(userId);
        cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(updatedItem.getQuantity());
                    item.setPrice(updatedItem.getPrice());
                    item.setProductName(updatedItem.getProductName());
                });
        return cartRepository.save(cart);
    }

    //  find cart without creating one
    public Optional<Cart> findCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }


    // Remove an item from the cart
    public Optional<Cart> removeItemFromCart(String userId, String itemId) {
        Optional<Cart> cartOptional = findCartByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getCartItems().removeIf(item -> item.getId().equals(itemId));
            return Optional.of(cartRepository.save(cart));
        }
        return Optional.empty();
    }

    // Clear the cart for a user
    public Optional<Cart> clearCart(String userId) {
        Optional<Cart> cartOptional = findCartByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getCartItems().clear();
            return Optional.of(cartRepository.save(cart));
        }
        return Optional.empty();
    }

    // Decrease the quantity of an existing item in the cart.
    public Cart decreaseItemQuantity(String userId, String itemId, int quantityToDecrease) {
        // Find the cart without creating one; if not found, throw an exception.
        Optional<Cart> cartOptional = findCartByUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new RuntimeException("Cart not found for user: " + userId);
        }
        Cart cart = cartOptional.get();

        // Find the cart item by itemId.
        Optional<CartItem> itemOptional = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
        if (itemOptional.isEmpty()) {
            throw new RuntimeException("Cart item not found with id: " + itemId);
        }
        CartItem cartItem = itemOptional.get();

        // Decrease the quantity.
        int newQuantity = cartItem.getQuantity() - quantityToDecrease;
        if (newQuantity < 1) {
            // If the new quantity is less than 1, remove the item from the cart.
            cart.getCartItems().remove(cartItem);
        } else {
            // Otherwise, update the quantity.
            cartItem.setQuantity(newQuantity);
        }

        // Save and return the updated cart.
        return cartRepository.save(cart);
    }
}
