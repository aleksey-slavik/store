package com.globallogic.store.domain.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

/**
 * Error entity, which generated when throws validation exceptions
 *
 * @author oleksii.slavik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    /**
     * error code
     */
    private String code;

    /**
     * error created date
     */
    private long timestamp;

    /**
     * error details
     */
    private Map<String, Object> details;

    public Error() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Append error code
     *
     * @param code error code
     */
    public Error code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Append error details
     *
     * @param details error details
     */
    public Error details(Map<String, Object> details) {
        this.details = details;
        return this;
    }

    /**
     * Append message to error details
     *
     * @param message error message
     */
    public Error message(String message) {
        if (details == null) {
            details = new HashMap<>();
        }

        details.put("message", message);
        return this;
    }

    /**
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return error created date (in milliseconds)
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return error details
     */
    public Map<String, Object> getDetails() {
        return details;
    }
}
