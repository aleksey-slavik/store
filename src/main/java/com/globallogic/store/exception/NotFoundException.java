package com.globallogic.store.exception;

/**
 * Exception throws when item with needed id not found
 *
 * @author oleksii.slavik
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
