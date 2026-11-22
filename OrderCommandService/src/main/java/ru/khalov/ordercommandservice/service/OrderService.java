package ru.khalov.ordercommandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khalov.ordercommandservice.client.PaymentClient;
import ru.khalov.ordercommandservice.client.dto.ChargeResult;
import ru.khalov.ordercommandservice.dto.request.OrderCreateRequest;
import ru.khalov.ordercommandservice.dto.response.OrderCreateResponse;
import ru.khalov.ordercommandservice.entity.Order;
import ru.khalov.ordercommandservice.repository.OrderRepository;
import ru.khalov.ordercommandservice.util.OrderStatus;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public OrderCreateResponse createOrder(Long userId, OrderCreateRequest request){
        BigDecimal price = calculatePrice(request);
        Order order = new Order(
                userId,
                request.basket(),
                price
        );

        Order saved = orderRepository.save(order);

        ChargeResult result = paymentClient.charge(saved.getId(), userId, price);

        if (result.success()) {
            saved.setStatus(OrderStatus.ACTIVE);
        } else {
            saved.setStatus(OrderStatus.CANCELED);
        }

        return OrderCreateResponse.accepted(orderRepository.save(saved));
    }

    private BigDecimal calculatePrice(OrderCreateRequest request){

        // временная заглушка

        return request.price();
    }
}
