package ru.khalov.paymantservice.client.dto;

public record DivideResult(
        boolean success
) {
    public static DivideResult decline(){
        return new DivideResult(false);
    }

    public static DivideResult ok(){
        return new DivideResult(true);
    }

}
