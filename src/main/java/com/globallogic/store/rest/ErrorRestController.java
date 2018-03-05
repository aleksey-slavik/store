package com.globallogic.store.rest;

import com.globallogic.store.domain.error.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@RestControllerAdvice
public class ErrorRestController {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex) {
        System.out.println(ex.getConstraintViolations());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        System.out.println(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    private List<Error> createError(Class entityClass, List<FieldError> fieldErrors) {
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            try {
                createErrorDetails(entityClass.getDeclaredField(fieldError.getField()).getDeclaredAnnotations());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            Error error = new Error()
                    .code(createErrorCode(fieldError));
            errors.add(error);
        }

        return errors;
    }

    private Map<String, Object> createErrorDetails(Annotation[] annotations) {
        Map<String, Object> details = new HashMap<>();

        for (Annotation annotation : annotations) {
            System.out.println(annotation);
            for (Method method : annotation.annotationType().getDeclaredMethods()) {
                System.out.println(method.getName());
            }
        }

        System.out.println();
        return details;
    }

    private String createErrorCode(FieldError error) {
        return error.getObjectName() + "." + error.getField() + "." + error.getCode();
    }
}
