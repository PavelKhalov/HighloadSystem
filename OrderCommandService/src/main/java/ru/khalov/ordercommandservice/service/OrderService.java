package ru.khalov.ordercommandservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khalov.ordercommandservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;



}
