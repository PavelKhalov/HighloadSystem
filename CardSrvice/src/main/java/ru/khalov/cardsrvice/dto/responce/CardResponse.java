package ru.khalov.cardsrvice.dto.responce;

import java.math.BigDecimal;

public record CardResponse (
        String lastFourDigits,
        BigDecimal balance
) {
}
