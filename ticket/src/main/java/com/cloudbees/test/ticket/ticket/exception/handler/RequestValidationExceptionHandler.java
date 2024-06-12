package com.cloudbees.test.ticket.ticket.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cloudbees.test.ticket.ticket.exception.RequestValidationException;

@ControllerAdvice
public class RequestValidationExceptionHandler {
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<String> handleServiceException(RequestValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
