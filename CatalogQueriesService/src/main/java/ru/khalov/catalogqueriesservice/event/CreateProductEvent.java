package ru.khalov.catalogqueriesservice.event;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductEvent(
        UUID id,
        Integer productId,
        String title,
        Long sellerId,
        Integer quantity,
        BigDecimal priceOneProduct
) {
}
