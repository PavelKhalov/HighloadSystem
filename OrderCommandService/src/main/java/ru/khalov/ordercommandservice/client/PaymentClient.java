package ru.khalov.ordercommandservice.client;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.khalov.ordercommandservice.client.dto.ChargeResult;
import ru.khalov.ordercommandservice.client.dto.request.ChargeRequest;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestClient restClient;
    private final AssertFalseValidator assertFalseValidator;

    public ChargeResult charge(Long orderId, Long userId, BigDecimal price){
        try{
            ChargeResult result = restClient.post()
                    .uri("/api/payments/charge")
                    .body(new ChargeRequest(orderId, userId, price))
                    .retrieve()
                    .body(ChargeResult.class);

            return result != null ? result : ChargeResult.declined();
        } catch (Exception e){
            return ChargeResult.declined();
        }
    }

}
