package ru.khalov.ordercommandservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.khalov.ordercommandservice.dto.BasketItems;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreateRequest(
        @NotEmpty(message = "Basket should not be empty")
        @Valid
        List<BasketItems> basket,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price
){
}