package ru.khalov.cardsrvice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.khalov.cardsrvice.dto.request.CreateCardRequest;
import ru.khalov.cardsrvice.dto.request.DepositRequest;
import ru.khalov.cardsrvice.dto.responce.CardResponse;
import ru.khalov.cardsrvice.dto.responce.ChargeResponse;
import ru.khalov.cardsrvice.entity.Card;
import ru.khalov.cardsrvice.repository.CardRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @Transactional
    public CardResponse createCard(Long userId,CreateCardRequest request){
        if(cardRepository.existsByUserId(userId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this user already have card");
        }

        Card card = new Card(
                request.cardNumber(),
                request.getLastFourDigits(),
                userId
        );

        cardRepository.save(card);

        return new CardResponse(request.getLastFourDigits(), request.startedBalance());
    }

    @Transactional
    public String deleteCard(Long userId){
        if(!cardRepository.existsByUserId(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this user dont have a card");
        }

        cardRepository.deleteByUserId(userId);

        return "Card deleted successfully!";
    }

    public CardResponse getCard(Long userId){
        Card card = cardRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "this user dont have a card"));

        return new CardResponse(card.getLastFourDigits(), card.getBalance());
    }

    @Transactional
    public String deposit(Long userId, DepositRequest request){
        Card card = cardRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "this user dont have a card"));

        BigDecimal currentBalance = card.getBalance();

        card.setBalance(currentBalance.add(request.amount()));

        cardRepository.flush();

        return "Deposit done!";
    }

    //// внутренние метод ////

    @Transactional
    public ChargeResponse debit(Long userId, BigDecimal amount){
        Card card = cardRepository.findByUserIdForUpdate(userId).orElse(null);

        if(card == null){
            return ChargeResponse.declined("Card not found");
        }

        if(card.getBalance().compareTo(amount) < 0){
            return ChargeResponse.declined("There isn’t enough money in the balance.");
        }

        card.setBalance(card.getBalance().subtract(amount));
        cardRepository.flush();

        return ChargeResponse.ok();
    }
}
