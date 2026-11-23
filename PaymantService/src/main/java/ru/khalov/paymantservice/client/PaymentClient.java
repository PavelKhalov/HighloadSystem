package ru.khalov.paymantservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.khalov.paymantservice.client.dto.DivideResult;
import ru.khalov.paymantservice.client.dto.request.CardDebitRequest;
import ru.khalov.paymantservice.client.dto.request.ChargeRequest;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestClient restClient;

    public DivideResult divide(ChargeRequest request){
        String url = "/internal/cards/" + request.userId().toString() + "/debit";
        try{
            DivideResult result = restClient
                    .post()
                    .uri(url)
                    .body(new CardDebitRequest(request.price()))
                    .retrieve()
                    .body(DivideResult.class);

            return  result != null ? result : DivideResult.decline();
        } catch (Exception e){
            return DivideResult.decline();
        }
    }
}
