package com.tomato.backend.controller;

import com.tomato.backend.dto.AddToCartRequest;
import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.dto.UpdateCartQuantityRequest;
import com.tomato.backend.entity.CartItem;
import com.tomato.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // The JwtAuthFilter sets the authenticated userId as the principal on the SecurityContext
    private String currentUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItem>>> getCart() {
        List<CartItem> cart = cartService.getCart(currentUserId());
        return ResponseEntity.ok(ApiResponse.success("Cart fetched", cart));
    }

    @GetMapping("/total")
    public ResponseEntity<ApiResponse<Map<String, Double>>> getCartTotal() {
        double total = cartService.getCartTotal(currentUserId());
        return ResponseEntity.ok(ApiResponse.success("Cart total calculated", Map.of("total", total)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItem>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartItem item = cartService.addToCart(currentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Item added to cart", item));
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItem>> updateQuantity(
            @PathVariable String cartItemId, @Valid @RequestBody UpdateCartQuantityRequest request) {
        CartItem updated = cartService.updateQuantity(currentUserId(), cartItemId, request.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Cart item updated", updated));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(@PathVariable String cartItemId) {
        cartService.removeFromCart(currentUserId(), cartItemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart(currentUserId());
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}
