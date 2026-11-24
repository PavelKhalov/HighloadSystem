package ru.khalov.orderqueriesservice.dto.response;

import ru.khalov.orderqueriesservice.dto.BasketItems;
import ru.khalov.orderqueriesservice.util.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record GetOrderResponse(
        Long userId,
        Long orderId,
        List<BasketItems> basketItems,
        BigDecimal price,
        OrderStatus status
) {
}
