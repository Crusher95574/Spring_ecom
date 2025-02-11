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
    public Cart addNewItemToCart(String userId,CartItem newItem){
        Cart cart = getOrCreateCart(userId);
        boolean updated = cart.getCartItems().stream().anyMatch(item -> {
            if(item.getProductId().equals(newItem.getProductId())){
                item.setQuantity(item.getQuantity()+ newItem.getQuantity());
                return true;
            }
            return false;
        });

        if(!updated){
            cart.addItem(newItem);
        }

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
    public Optional<Cart> removeItemFromCart(String userId, Long itemId) {
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
}
