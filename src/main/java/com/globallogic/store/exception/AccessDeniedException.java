package com.globallogic.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Access denied exception
 *
 * @author oleksii.slavik
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends Exception {
}