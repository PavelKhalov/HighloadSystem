package ru.khalov.cardsrvice.dto.responce;

public record ChargeResponse(
        boolean success,
        String reason
) {
    public static  ChargeResponse ok(){
        return new ChargeResponse(true, null);
    }

    public static ChargeResponse declined(String reason){
        return new ChargeResponse(false, reason);
    }
}
