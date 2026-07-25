package com.tomato.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId;
    private List<OrderItem> items;
    private double totalAmount;

    @Builder.Default
    private String status = "PLACED"; // PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
