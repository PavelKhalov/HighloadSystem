package ru.khalov.cardsrvice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DepositRequest(
        @NotBlank
        @DecimalMin(value = "0.01", message = "The minimum deposit amount is 0.01.")
        BigDecimal amount
){
}
