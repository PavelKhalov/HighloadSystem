package ru.khalov.ordercommandservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BasketItems(
        @NotNull(message = "Product id is required")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Minimal quantity is 1")
        Integer quantity
) {
}
