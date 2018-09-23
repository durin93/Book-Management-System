package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class SecurityRestControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(SecurityRestControllerAdvice.class);

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> unAuthentication(UnAuthenticationException e) {
        log.debug("RestController UnAuthenticationException is happened!"+ e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Void> unAuthorization(UnAuthorizationException e) {
        log.debug("RestController UnAuthorizationException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> nullPointException(NullPointerException e) {
        log.debug("RestController NullPointerException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<Void> deleteException(DeleteException e) {
        log.debug("RestController DeleteException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(JwtAuthorizationException.class)
    public ResponseEntity<Void> jwtAuthorizationException(JwtAuthorizationException e) {
        log.debug("RestController jwtAuthorizationException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(RentalException.class)
    public ResponseEntity<Void> rentalException(RentalException e) {
        log.debug("RestController rentalException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
