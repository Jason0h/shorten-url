package com.example.shortenurl.exceptions;

public class UrlIdDoesNotExistException extends RuntimeException {
    public UrlIdDoesNotExistException(long id) {
        super("UrlInfo row of id: " + id + " does not exist in the database.");
    }
}
