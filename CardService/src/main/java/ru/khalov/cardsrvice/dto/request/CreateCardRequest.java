package ru.khalov.cardsrvice.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateCardRequest (
        @NotBlank(message = "Card number must be provided")
        @Pattern(regexp = "\\d{16}", message = "Card number should have 16 digits")
        String cardNumber,

        @NotNull(message = "Started balance must be provided")
        @DecimalMin(value = "0.00", message = "balance should not be lover than 0")
        @Digits(integer = 9, fraction = 2, message = "Uncorrected sum format")
        BigDecimal startedBalance
){
    public String getLastFourDigits(){
        return cardNumber.substring(11, 15);
    }
}
