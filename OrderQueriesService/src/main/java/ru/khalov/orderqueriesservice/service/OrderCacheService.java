package ru.khalov.orderqueriesservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.khalov.orderqueriesservice.dto.response.GetOrderResponse;
import ru.khalov.orderqueriesservice.entity.Order;
import ru.khalov.orderqueriesservice.event.OrderCreateEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCacheService {

    private final RedisTemplate<String, Order> redisTemplate;

    @KafkaListener(topics = "order-topic")
    public void addInCache(OrderCreateEvent event){
        var order = new Order();
        order.setId(event.getOrderId());
        order.setUserId(event.getUserId());
        order.setBasket(event.getBasket());
        order.setPrice(event.getPrice());
        order.setStatus(event.getStatus());
        order.setCreatedAt(event.getCreatedAt());

        try{
            redisTemplate.opsForValue().set("order:" + order.getId(), order);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public GetOrderResponse findOrderById(Long userId, Long orderId){
        Order order = redisTemplate.opsForValue().get("order:"+ orderId);

        if(order != null){
            if(!order.getUserId().equals(userId))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Its not your order");

            return new GetOrderResponse(userId, order.getId(), order.getBasket(), order.getPrice(), order.getStatus());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }
}
