package com.example.shortenurl.exceptions;

public class ShortCodeDoesNotExistException extends RuntimeException {
    public ShortCodeDoesNotExistException(String shortCode) {
        super(shortCode + " is not registered as an existing shortCode in the application.");
    }
}
