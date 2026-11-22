package ru.khalov.ordercommandservice.event;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateOrderEvent {
    private List<Integer> basket;
    private BigDecimal price;
    private Long userId;

    public CreateOrderEvent(){}

    public CreateOrderEvent(List<Integer> basket, BigDecimal price, Long userId){
        this.basket = basket;
        this.price = price;
        this.userId = userId;
    }
}
