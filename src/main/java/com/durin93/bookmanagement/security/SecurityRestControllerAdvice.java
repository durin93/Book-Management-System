package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class SecurityRestControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(SecurityRestControllerAdvice.class);

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> unAuthentication(UnAuthenticationException e) {
        log.debug("RestController UnAuthenticationException is happened!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Void> unAuthorization(UnAuthorizationException e) {
        log.debug("RestController UnAuthorizationException is happened!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> nullPointException(NullPointerException e) {
        log.debug("RestController NullPointerException is happened!");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}