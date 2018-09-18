package com.durin93.bookmanagement.exception;

import com.durin93.bookmanagement.support.domain.ErrorManager;

public class RentalException extends RuntimeException {

    public RentalException(String message) {
        super(message);
    }

    public RentalException(ErrorManager errorManager) {
        super(errorManager.getMessage());
    }

}
