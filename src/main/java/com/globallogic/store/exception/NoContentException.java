package com.globallogic.store.exception;

/**
 * Exception throws when return empty list of items
 *
 * @author oleksii.slavik
 */
public class NoContentException extends Exception {

    public NoContentException(String message) {
        super(message);
    }
}
