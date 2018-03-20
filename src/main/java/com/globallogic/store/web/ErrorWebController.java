package com.globallogic.store.web;

import com.globallogic.store.exception.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web errors controller
 *
 * @author oleksii.slavik
 */
@ControllerAdvice
public class ErrorWebController {

    /**
     * Access denied exception controller
     *
     * @return view with custom access denied page
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied() {
        return new ModelAndView("error/403");
    }
}
