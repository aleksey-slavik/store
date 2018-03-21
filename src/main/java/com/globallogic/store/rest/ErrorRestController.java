package com.globallogic.store.rest;

import com.globallogic.store.domain.error.Error;
import com.globallogic.store.exception.AlreadyExistException;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
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
     * Errors of validation of the object received from the client
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
            String object = violation.getRootBeanClass().getSimpleName();
            String field = violation.getPropertyPath().toString();
            String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            Error error = new Error()
                    .code(object + "." + field + "." + code)
                    .details(violation.getConstraintDescriptor().getAttributes());
            errors.add(error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * NotFoundException handler
     *
     * @see NotFoundException
     * @param exception thrown exception
     * @return error details
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        Error error = new Error()
                .code("NotFoundException")
                .message(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * NoContentException handler
     *
     * @see NoContentException
     * @param exception thrown exception
     * @return error details
     */
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> handleNoContentException(NoContentException exception) {
        Error error = new Error()
                .code("NoContentException")
                .message(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    /**
     * NotAcceptableException handler
     *
     * @see NotAcceptableException
     * @param exception thrown exception
     * @return error details
     */
    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<?> handleNotAcceptableException(NotAcceptableException exception) {
        Error error = new Error()
                .code("NotAcceptableException")
                .message(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    /**
     * AlreadyExistException handler
     *
     * @see AlreadyExistException
     * @param exception thrown exception
     * @return error details
     */
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleNotAcceptableException(AlreadyExistException exception) {
        Error error = new Error()
                .code("AlreadyExistException")
                .message(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
