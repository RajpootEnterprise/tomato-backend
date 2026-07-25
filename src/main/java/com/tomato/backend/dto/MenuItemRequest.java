package com.tomato.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MenuItemRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "category is required")
    private String category;

    private String description;

    @Positive(message = "price must be positive")
    private double price;

    private double rating;

    private String imageUrl;
}
