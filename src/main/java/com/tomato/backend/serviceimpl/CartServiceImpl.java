package com.tomato.backend.serviceimpl;

import com.tomato.backend.dto.AddToCartRequest;
import com.tomato.backend.entity.CartItem;
import com.tomato.backend.entity.MenuItem;
import com.tomato.backend.exceptions.ResourceNotFoundException;
import com.tomato.backend.repository.CartItemRepository;
import com.tomato.backend.repository.MenuItemRepository;
import com.tomato.backend.service.CartService;
import com.tomato.backend.utils.PriceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public CartItem addToCart(String userId, AddToCartRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuItemId()));

        // If the item is already in the cart, just bump the quantity instead of duplicating rows
        CartItem cartItem = cartItemRepository.findByUserIdAndMenuItemId(userId, request.getMenuItemId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.getQuantity());
                    return existing;
                })
                .orElse(CartItem.builder()
                        .userId(userId)
                        .menuItemId(menuItem.getId())
                        .menuItemName(menuItem.getName())
                        .price(menuItem.getPrice())
                        .quantity(request.getQuantity())
                        .build());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateQuantity(String userId, String cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for this user");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(String userId, String cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for this user");
        }

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    @Override
    public double getCartTotal(String userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        return PriceUtil.calculateCartTotal(items);
    }
}
