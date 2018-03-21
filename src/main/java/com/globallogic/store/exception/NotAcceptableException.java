package com.globallogic.store.exception;

/**
 * Exception throws during update/create errors
 *
 * @author oleksii.slavik
 */
public class NotAcceptableException extends Exception {

    public NotAcceptableException(String message) {
        super(message);
    }
}
