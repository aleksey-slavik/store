package com.globallogic.store.dto.error;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto {

    private List<FieldErrorDto> errors = new ArrayList<>();

    public void addError(String path, String message) {
        FieldErrorDto error = new FieldErrorDto(path, message);
        errors.add(error);
    }

    public List<FieldErrorDto> getErrors() {
        return errors;
    }
}
