package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class SecurityRestControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(SecurityRestControllerAdvice.class);

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> unAuthentication(UnAuthenticationException e) {
        logger.debug("RestController UnAuthenticationException is happened!"+ e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Void> unAuthorization(UnAuthorizationException e) {
        logger.debug("RestController UnAuthorizationException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> nullPointException(NullPointerException e) {
        logger.debug("RestController NullPointerException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<Void> deleteException(DeleteException e) {
        logger.debug("RestController DeleteException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(JwtAuthorizationException.class)
    public ResponseEntity<Void> jwtAuthorizationException(JwtAuthorizationException e) {
        logger.debug("RestController jwtAuthorizationException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(RentalException.class)
    public ResponseEntity<Void> rentalException(RentalException e) {
        logger.debug("RestController rentalException is happened!" + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
