package com.tomato.backend.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartQuantityRequest {

    @Min(value = 0, message = "quantity cannot be negative")
    private int quantity;
}
