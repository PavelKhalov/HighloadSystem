package ru.khalov.ordercommandservice.client.dto;

import ru.khalov.ordercommandservice.util.CancellationReason;

public record ChargeResult(
        boolean success
) {
    public static  ChargeResult declined(){
        return  new ChargeResult(false);
    }
}
