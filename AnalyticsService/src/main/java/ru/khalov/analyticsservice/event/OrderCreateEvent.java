package ru.khalov.analyticsservice.event;

import lombok.Getter;
import lombok.Setter;
import ru.khalov.analyticsservice.dto.BasketItems;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderCreateEvent {

    private UUID eventId;
    private Long orderId;
    private List<BasketItems> basket;
    private Long userId;
    private BigDecimal price;
    private String status;
    private Instant createdAt;

    public OrderCreateEvent(){}

    public OrderCreateEvent(UUID eventId, Long orderId,
                            List<BasketItems> basket, Long userId,
                            BigDecimal price, String status, Instant createdAt){
        this.eventId = eventId;
        this.orderId = orderId;
        this.basket = basket;
        this.userId = userId;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }
}
