package com.globallogic.store.domain.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    private String code;
    private String url;
    private long timestamp;
    private ErrorDetails details;

    public Error() {
        this.timestamp = System.currentTimeMillis();
    }

    public Error code(String code) {
        this.code = code;
        return this;
    }

    public Error url(String url) {
        this.url = url;
        return this;
    }

    public Error details(ErrorDetails details) {
        this.details = details;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ErrorDetails getDetails() {
        return details;
    }
}
