package com.tomato.backend.serviceimpl;

import com.tomato.backend.entity.CartItem;
import com.tomato.backend.entity.Order;
import com.tomato.backend.entity.OrderItem;
import com.tomato.backend.exceptions.BadRequestException;
import com.tomato.backend.exceptions.ResourceNotFoundException;
import com.tomato.backend.repository.CartItemRepository;
import com.tomato.backend.repository.OrderRepository;
import com.tomato.backend.service.OrderService;
import com.tomato.backend.utils.PriceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order placeOrder(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cannot place an order with an empty cart");
        }

        List<OrderItem> orderItems = cartItems.stream()
                .map(ci -> OrderItem.builder()
                        .menuItemId(ci.getMenuItemId())
                        .menuItemName(ci.getMenuItemName())
                        .price(ci.getPrice())
                        .quantity(ci.getQuantity())
                        .build())
                .collect(Collectors.toList());

        double total = PriceUtil.calculateCartTotal(cartItems);

        Order order = Order.builder()
                .userId(userId)
                .items(orderItems)
                .totalAmount(total)
                .status("PLACED")
                .build();

        Order savedOrder = orderRepository.save(order);

        // empty the cart now that the order has been captured
        cartItemRepository.deleteByUserId(userId);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersForUser(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Order getOrderById(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Order not found for this user");
        }

        return order;
    }

    @Override
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
