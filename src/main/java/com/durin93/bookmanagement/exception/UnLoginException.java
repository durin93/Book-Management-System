package com.durin93.bookmanagement.exception;

public class UnLoginException extends RuntimeException {

    public UnLoginException() {
    }

    public UnLoginException(String message) {
        super(message);
    }
}
