package ru.khalov.orderqueriesservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.khalov.orderqueriesservice.dto.request.GetOrderRequest;
import ru.khalov.orderqueriesservice.dto.response.GetOrderResponse;
import ru.khalov.orderqueriesservice.service.OrderCacheService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderCacheService orderCacheService;

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@AuthenticationPrincipal Jwt jwt,
                                                     @PathVariable Long orderId){
        Long userId = jwt.getClaim("publicId");
        return ResponseEntity.ok().body(orderCacheService.findOrderById(userId, orderId));
    }
}
