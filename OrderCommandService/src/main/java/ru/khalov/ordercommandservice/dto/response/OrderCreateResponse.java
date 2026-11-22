package ru.khalov.ordercommandservice.dto.response;

import ru.khalov.ordercommandservice.dto.BasketItems;
import ru.khalov.ordercommandservice.entity.Order;
import ru.khalov.ordercommandservice.util.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderCreateResponse(
        Long orderId,
        OrderStatus status,
        List<BasketItems> basket,
        BigDecimal price,
        Instant createdAt
) {
    public static OrderCreateResponse accepted(Order order){
        return new OrderCreateResponse(
                order.getId(),
                order.getStatus(),
                order.getBasket(),
                order.getPrice(),
                order.getCreatedAt()
        );
    }
}
