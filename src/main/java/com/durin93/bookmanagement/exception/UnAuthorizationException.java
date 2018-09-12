package com.durin93.bookmanagement.exception;

public class UnAuthorizationException extends RuntimeException {

    public UnAuthorizationException() {
    }

    public UnAuthorizationException(String message) {
        super(message);
    }
}
