package com.globallogic.store.domain.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    private String code;
    private long timestamp;
    private Map<String, Object> details;

    public Error() {
        this.timestamp = System.currentTimeMillis();
    }

    public Error code(String code) {
        this.code = code;
        return this;
    }

    public Error details(Map<String, Object> details) {
        this.details = details;
        return this;
    }

    public String getCode() {
        return code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
