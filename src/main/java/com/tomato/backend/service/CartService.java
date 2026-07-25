package com.tomato.backend.service;

import com.tomato.backend.dto.AddToCartRequest;
import com.tomato.backend.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCart(String userId);
    CartItem addToCart(String userId, AddToCartRequest request);
    CartItem updateQuantity(String userId, String cartItemId, int quantity);
    void removeFromCart(String userId, String cartItemId);
    void clearCart(String userId);
    double getCartTotal(String userId);
}
