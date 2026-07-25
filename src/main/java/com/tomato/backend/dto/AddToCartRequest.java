package com.tomato.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddToCartRequest {

    @NotBlank(message = "menuItemId is required")
    private String menuItemId;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity = 1;
}
