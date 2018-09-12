package com.durin93.bookmanagement.exception;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
    }

    public UnAuthenticationException(String message) {
        super(message);
    }
}
