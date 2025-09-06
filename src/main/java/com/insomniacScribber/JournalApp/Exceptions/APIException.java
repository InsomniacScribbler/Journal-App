package com.insomniacScribber.JournalApp.Exceptions;

public class APIException extends RuntimeException{

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
