package ru.khalov.orderqueriesservice.entity;

import lombok.Getter;
import lombok.Setter;
import ru.khalov.orderqueriesservice.dto.BasketItems;
import ru.khalov.orderqueriesservice.util.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Getter
@Setter
public class Order {
    private Long id;
    private Long userId;
    private List<BasketItems> basket;
    private BigDecimal price;
    private OrderStatus status;
    private Instant createdAt;

    public Order(){}

}
