package com.globallogic.store.rest;

import com.globallogic.store.domain.error.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Api validation exception handler
 *
 * @author oleksii.slavik
 */
@RestControllerAdvice
public class ErrorRestController {

    /**
     * MethodArgumentNotValidException handler
     *
     * @param exception thrown exception
     * @return error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException exception) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Object entity = exception.getBindingResult().getTarget();
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        List<Error> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            Error error = new Error();
            String object = violation.getRootBeanClass().getSimpleName();
            String field = violation.getPropertyPath().toString();
            String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            error.code(object + "." + field + "." + code);
            error.details(violation.getConstraintDescriptor().getAttributes());
            errors.add(error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
