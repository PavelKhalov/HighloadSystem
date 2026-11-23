package ru.khalov.catalogcommandsservice.event;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductEvent (
        UUID id,
        Integer productId,
        String title,
        Long sellerId,
        BigDecimal priceOneProduct
){
}
