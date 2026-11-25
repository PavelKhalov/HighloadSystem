package ru.khalov.orderqueriesservice.dto.response;

import ru.khalov.orderqueriesservice.entity.Order;

import java.util.List;

public record GetAllOrderResponse(
        List<Order> orders
) {
}
