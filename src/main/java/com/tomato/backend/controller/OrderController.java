package com.tomato.backend.controller;

import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.entity.Order;
import com.tomato.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private String currentUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder() {
        Order order = orderService.placeOrder(currentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Order placed successfully", order));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders() {
        List<Order> orders = orderService.getOrdersForUser(currentUserId());
        return ResponseEntity.ok(ApiResponse.success("Orders fetched", orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId, currentUserId());
        return ResponseEntity.ok(ApiResponse.success("Order fetched", order));
    }

    // In a real app, restrict this to admins via role check
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Order>> updateStatus(
            @PathVariable String orderId, @RequestBody Map<String, String> body) {
        Order updated = orderService.updateOrderStatus(orderId, body.get("status"));
        return ResponseEntity.ok(ApiResponse.success("Order status updated", updated));
    }
}
