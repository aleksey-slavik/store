package com.globallogic.store.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ErrorRestController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        for (FieldError fieldError : result.getFieldErrors()) {
        System.out.println(fieldError);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleArgumentNotValid(ConstraintViolationException exception){
        System.out.println(exception.getConstraintViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
