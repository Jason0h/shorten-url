package com.example.shortenurl.controllers;

import com.example.shortenurl.exceptions.LongUrlAlreadyExistsException;
import com.example.shortenurl.exceptions.LongUrlIsNotValidException;
import com.example.shortenurl.exceptions.ShortCodeDoesNotExistException;
import com.example.shortenurl.models.ErrorDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(LongUrlAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> longUrlAlreadyExistsExceptionHandler(LongUrlAlreadyExistsException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(LongUrlIsNotValidException.class)
    public ResponseEntity<ErrorDetails> longUrlIsNotValidExceptionHandler(LongUrlIsNotValidException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(ShortCodeDoesNotExistException.class)
    public ResponseEntity<ErrorDetails> shortCodeDoesNotExistExceptionHandler(ShortCodeDoesNotExistException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
