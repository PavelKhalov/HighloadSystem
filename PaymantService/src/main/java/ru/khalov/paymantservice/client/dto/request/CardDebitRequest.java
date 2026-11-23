package ru.khalov.paymantservice.client.dto.request;

import java.math.BigDecimal;

public record CardDebitRequest(
        BigDecimal price
) {
}
