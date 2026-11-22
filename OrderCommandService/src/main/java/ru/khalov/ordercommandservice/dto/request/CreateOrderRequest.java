package ru.khalov.ordercommandservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest (
        @NotBlank(message = "Basket should not be empty")
        List<Integer> basket,

        BigDecimal price
){
}
