package com.globallogic.store.web;

import com.globallogic.store.exception.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorWebController {

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied() {
        return new ModelAndView("error/403");
    }
}
