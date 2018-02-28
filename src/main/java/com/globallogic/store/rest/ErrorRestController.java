package com.globallogic.store.rest;

import com.globallogic.store.domain.error.Error;
import com.globallogic.store.domain.error.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorRestController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createError(ex.getBindingResult().getFieldErrors()));
    }

    private List<Error> createError(List<FieldError> fieldErrors) {
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            Error error = new Error()
                    .code(createErrorCode(fieldError))
                    .details(createErrorDetails(fieldError));
            errors.add(error);
        }

        return errors;
    }

    private ErrorDetails createErrorDetails(FieldError error) {
        return new ErrorDetails()
                .field(error.getField())
                .value(error.getRejectedValue())
                .message(error.getDefaultMessage());
    }

    private String createErrorCode(FieldError error) {
        return error.getObjectName() + "." + error.getField() + "." + error.getCode();
    }
}
