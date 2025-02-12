package com.example.ecommerce_platform.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {
    @NotBlank(message = "Order status is required")
    private String status;
}
