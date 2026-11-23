package ru.khalov.catalogcommandsservice.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Price must be provided")
        @Positive(message = "Price must be positive")
        @DecimalMin(value = "0.01", message = "Min decimal value is: 0.01")
        BigDecimal priceOneProduct,

        @NotNull(message = "Quantity must be provided")
        @Positive(message = "Quantity must be positive")
        @Min(value = 1, message = "Min quantity is 1")
        Integer quantity
) {
}
