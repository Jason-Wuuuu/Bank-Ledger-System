package dev.codescreen.exeption;

public class EventStoreException extends RuntimeException {
    public EventStoreException(String message) {
        super(message);
    }
}