package com.globallogic.store.web;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class DeleteTemplate extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        ModelAndView mav = new ModelAndView();
        RestTemplate template = new RestTemplate();
        template.delete(getRestPath() + id);
        mav.setViewName(getSuccessViewName());
        return mav;
    }

    public abstract String getRestPath();
    public abstract String getSuccessViewName();
}
