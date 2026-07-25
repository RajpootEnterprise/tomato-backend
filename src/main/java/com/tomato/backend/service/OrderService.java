package com.tomato.backend.service;

import com.tomato.backend.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(String userId);
    List<Order> getOrdersForUser(String userId);
    Order getOrderById(String orderId, String userId);
    Order updateOrderStatus(String orderId, String status);
    List<Order> getAllOrders();
}
