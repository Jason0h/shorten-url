package com.example.shortenurl.exceptions;

public class LongUrlAlreadyExistsException extends RuntimeException {
    public LongUrlAlreadyExistsException(String longUrl) {
        super(longUrl + " has already been registered as a longUrl in the application.");
    }
}
