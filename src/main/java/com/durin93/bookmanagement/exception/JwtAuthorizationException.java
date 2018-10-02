package com.durin93.bookmanagement.exception;

public class JwtAuthorizationException extends RuntimeException {

    public JwtAuthorizationException() {
    }

    public JwtAuthorizationException(String message) {
        super(message);
    }

}
