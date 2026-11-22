package ru.khalov.cardsrvice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khalov.cardsrvice.dto.request.ChargeRequest;
import ru.khalov.cardsrvice.dto.responce.ChargeResponse;
import ru.khalov.cardsrvice.service.CardService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/cards")
public class CardInternalController {

    private final CardService cardService;

    @GetMapping("/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCard(userId).balance());
    }

    @PostMapping("/{userId}/debit")
    public ResponseEntity<ChargeResponse> debitMoney(@PathVariable Long userId, @RequestBody @Valid ChargeRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.debit(userId, request.amount()));
    }
}
