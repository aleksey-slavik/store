package com.globallogic.store.exception;

/**
 * Exception throws when insert data, which already exist in database
 *
 * @author oleksii.slavik
 */
public class AlreadyExistException extends Exception {

    public AlreadyExistException(String message) {
        super(message);
    }
}
