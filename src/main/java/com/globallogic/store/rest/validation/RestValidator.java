package com.globallogic.store.rest.validation;

import com.globallogic.store.domain.error.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestValidator<E> {

    private Validator validator;

    public RestValidator () {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public ResponseEntity<?> validate(E entity, Executable executable) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            List<Error> errors = new ArrayList<>();

            for (ConstraintViolation<E> violation : violations) {
                Error error = new Error();
                String object = violation.getRootBeanClass().getSimpleName();
                String field = violation.getPropertyPath().toString();
                String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                error.code(object + "." + field + "." + code);
                error.details(violation.getConstraintDescriptor().getAttributes());
                errors.add(error);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } else {
            return executable.execute();
        }
    }
}
