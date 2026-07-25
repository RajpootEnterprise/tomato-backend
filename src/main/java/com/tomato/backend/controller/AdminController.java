package com.tomato.backend.controller;

import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.entity.Order;
import com.tomato.backend.repository.OrderRepository;
import com.tomato.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        long totalUsers = userRepository.count();
        List<Order> allOrders = orderRepository.findAll();
        long totalOrders = allOrders.size();

        double totalRevenue = 0;
        Map<String, Integer> statusBreakdown = new HashMap<>();

        statusBreakdown.put("PLACED", 0);
        statusBreakdown.put("PREPARING", 0);
        statusBreakdown.put("ON_THE_WAY", 0);
        statusBreakdown.put("DELIVERED", 0);
        statusBreakdown.put("CANCELLED", 0);

        for (Order order : allOrders) {
            String status = order.getStatus();
            statusBreakdown.put(status, statusBreakdown.getOrDefault(status, 0) + 1);

            if ("DELIVERED".equalsIgnoreCase(status)) {
                totalRevenue += order.getTotalAmount();
            }
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("totalOrders", totalOrders);
        stats.put("totalRevenue", totalRevenue);
        stats.put("ordersByStatus", statusBreakdown);

        return ResponseEntity.ok(ApiResponse.success("Admin stats fetched", stats));
    }
}
