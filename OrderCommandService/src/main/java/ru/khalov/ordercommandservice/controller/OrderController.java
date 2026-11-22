package ru.khalov.ordercommandservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.khalov.ordercommandservice.dto.request.OrderCreateRequest;
import ru.khalov.ordercommandservice.dto.response.OrderCreateResponse;
import ru.khalov.ordercommandservice.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderCreateResponse> create(@AuthenticationPrincipal Jwt jwt,
                                                      @RequestBody @Valid OrderCreateRequest request){
        Long userId = jwt.getClaim("publicId");

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, request));
    }
}
