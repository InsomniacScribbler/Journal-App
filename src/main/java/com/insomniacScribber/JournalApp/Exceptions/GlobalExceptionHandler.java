package com.insomniacScribber.JournalApp.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myApiException(APIException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message,  HttpStatus.BAD_REQUEST);
    }
}
