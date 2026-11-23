package ru.khalov.ordercommandservice.client.dto;


public record ChargeResult(
        boolean success
) {
    public static  ChargeResult declined(){
        return  new ChargeResult(false);
    }
}
