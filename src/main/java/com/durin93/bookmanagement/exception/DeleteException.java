package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.exception.ErrorManager;

public class DeleteException extends RuntimeException {

    public DeleteException() {
    }

    public DeleteException(String message) {
        super(message);
    }

}
