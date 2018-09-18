package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.domain.ErrorManager;

public class UnAuthorizationException extends RuntimeException {

    public UnAuthorizationException() {
    }

    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(ErrorManager errorManager) {
        super(errorManager.getMessage());
    }
}
