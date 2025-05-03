package com.example.shortenurl.exceptions;

public class LongUrlIsNotValidException extends RuntimeException {
    public LongUrlIsNotValidException(String longUrl) {
        super(longUrl + " does not match the syntax of a valid HTTP URL.");
    }
}
