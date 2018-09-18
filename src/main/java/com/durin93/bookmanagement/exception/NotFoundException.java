package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.domain.ErrorManager;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ErrorManager errorManager) {
        super(errorManager.getMessage());
    }

}
