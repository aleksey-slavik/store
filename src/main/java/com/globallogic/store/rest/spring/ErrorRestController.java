package com.globallogic.store.rest.spring;

import com.globallogic.store.dto.error.ValidationErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ErrorRestController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto processValidationError(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        return processFieldErrors(errors);
    }

    private ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDto dto = new ValidationErrorDto();

        for (FieldError fieldError: fieldErrors) {
            dto.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return dto;
    }

}
