package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.domain.ErrorManager;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
    }

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(ErrorManager errorManager) {
        super(errorManager.getMessage());
    }
}
