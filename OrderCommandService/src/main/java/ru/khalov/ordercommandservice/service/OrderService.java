package ru.khalov.ordercommandservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.khalov.ordercommandservice.client.PaymentClient;
import ru.khalov.ordercommandservice.client.dto.ChargeResult;
import ru.khalov.ordercommandservice.dto.request.OrderCreateRequest;
import ru.khalov.ordercommandservice.dto.response.OrderCreateResponse;
import ru.khalov.ordercommandservice.entity.Order;
import ru.khalov.ordercommandservice.event.OrderCreateEvent;
import ru.khalov.ordercommandservice.repository.OrderRepository;
import ru.khalov.ordercommandservice.util.OrderStatus;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
            saved = orderRepository.saveAndFlush(saved);

            OrderCreateEvent event = new OrderCreateEvent(
                    UUID.randomUUID(),
                    saved.getId(),
                    saved.getBasket(),
                    userId,
                    saved.getPrice(),
                    saved.getCreatedAt()
            );

            ProducerRecord record = new ProducerRecord<>("order-topic", String.valueOf(saved.getId()), event);

            CompletableFuture<SendResult<String, OrderCreateEvent>> future = kafkaTemplate.send(record);
            future.whenComplete((res, exception) -> {
                if(exception != null){
                    log.error("Send message failed");
                } else {
                    log.debug("Send message successful");
                }
            });
        } else {
            saved.setStatus(OrderStatus.CANCELED);
            saved = orderRepository.saveAndFlush(saved);
        }


        return OrderCreateResponse.accepted(orderRepository.save(saved));
    }

    private BigDecimal calculatePrice(OrderCreateRequest request){

        // временная заглушка

        return request.price();
    }
}
