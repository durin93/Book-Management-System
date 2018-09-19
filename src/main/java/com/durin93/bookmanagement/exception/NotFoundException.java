package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.exception.ErrorManager;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }


}
