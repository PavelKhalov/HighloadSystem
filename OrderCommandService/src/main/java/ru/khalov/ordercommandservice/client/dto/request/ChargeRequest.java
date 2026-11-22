package ru.khalov.ordercommandservice.client.dto.request;

import java.math.BigDecimal;

public record ChargeRequest(
        Long orderId,
        Long userId,
        BigDecimal price
) {
}
