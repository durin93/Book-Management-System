package com.durin93.bookmanagement.support.domain;

import org.springframework.beans.factory.annotation.Value;

public class ExceptionMessage {

    @Value("${exception.book.alreadyRent}")
    public static String CANT;

}
