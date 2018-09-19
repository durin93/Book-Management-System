package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.exception.ErrorManager;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
    }

    public UnAuthenticationException(String message) {
        super(message);
    }

}
