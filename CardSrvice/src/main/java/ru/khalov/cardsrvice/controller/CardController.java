package ru.khalov.cardsrvice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.khalov.cardsrvice.dto.request.CreateCardRequest;
import ru.khalov.cardsrvice.dto.request.DepositRequest;
import ru.khalov.cardsrvice.dto.responce.CardResponse;
import ru.khalov.cardsrvice.service.CardService;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/myCard")
    public ResponseEntity<CardResponse> getCard(@AuthenticationPrincipal Jwt jwt){
        Long userId = jwt.getClaim("publicId");
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCard(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<CardResponse> createCard(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestBody @Valid CreateCardRequest request){
        Long userId = jwt.getClaim("publicId");
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(userId, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCard(@AuthenticationPrincipal Jwt jwt){
        Long userId = jwt.getClaim("publicId");
        return ResponseEntity.status(HttpStatus.OK).body(cardService.deleteCard(userId));
    }

    @PostMapping
    public ResponseEntity<String> deposit(@AuthenticationPrincipal Jwt jwt,
                                          @RequestBody @Valid DepositRequest request){
        Long userId = jwt.getClaim("publicId");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cardService.deposit(userId, request));
    }
}
