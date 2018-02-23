package com.globallogic.store.domain.error;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {

    private String field;
    private Object value;
    private String message;
    private Integer maxLength;
    private Integer minLength;
    private Integer minValue;

    public ErrorDetails field(String field) {
        this.field = field;
        return this;
    }

    public ErrorDetails value(Object value) {
        this.value = value;
        return this;
    }

    public ErrorDetails message(String message) {
        this.message = message;
        return this;
    }

    public ErrorDetails maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public ErrorDetails minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public ErrorDetails minValue(Integer minValue) {
        this.minValue = minValue;
        return this;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMinValue() {
        return minValue;
    }
}
