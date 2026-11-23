package ru.khalov.paymantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khalov.paymantservice.client.PaymentClient;
import ru.khalov.paymantservice.client.dto.DivideResult;
import ru.khalov.paymantservice.client.dto.request.ChargeRequest;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentClient paymentClient;

    @PostMapping("/charge")
    public ResponseEntity<DivideResult> debitMoney(@RequestBody ChargeRequest request){
        return ResponseEntity.ok().body(paymentClient.divide(request));
    }
}
