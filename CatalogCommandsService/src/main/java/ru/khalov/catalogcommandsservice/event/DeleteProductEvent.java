package ru.khalov.catalogcommandsservice.event;

import java.util.UUID;

public record DeleteProductEvent(
        UUID id,
        Long sellerId,
        Integer productId
) {
}
