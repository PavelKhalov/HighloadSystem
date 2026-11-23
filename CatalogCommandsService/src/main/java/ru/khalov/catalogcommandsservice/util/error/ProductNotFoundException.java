package ru.khalov.catalogcommandsservice.util.error;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
