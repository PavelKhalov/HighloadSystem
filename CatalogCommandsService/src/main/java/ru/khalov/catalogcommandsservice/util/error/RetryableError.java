package ru.khalov.catalogcommandsservice.util.error;

import java.time.LocalDateTime;

public class RetryableError extends RuntimeException{
    private String message;
    private LocalDateTime timestamp;

    public RetryableError(String message, LocalDateTime timestamp){
        super(message);
        this.timestamp = timestamp;
    }

    public RetryableError(Throwable cause, String message, LocalDateTime timestamp){
        super(cause);
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
