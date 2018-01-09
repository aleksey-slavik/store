package com.globallogic.store.web;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;

public abstract class CreateTemplate<T> extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return get();
        } else if (method.equals(METHOD_POST)) {
            return post(httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView get() {
        return new ModelAndView(getFormViewName());
    }

    private ModelAndView post(HttpServletRequest httpServletRequest) {
        ModelAndView mav = new ModelAndView();
        HttpEntity<T> entity = new HttpEntity<T>(getItemFromRequest(httpServletRequest));
        RestTemplate template = new RestTemplate();
        template.postForEntity(postRestPath(), entity, getType());
        mav.setViewName(getSuccessViewName());
        return mav;
    }

    private Class<T> getType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract String getFormViewName();
    public abstract String getSuccessViewName();
    public abstract String postRestPath();
    public abstract T getItemFromRequest(HttpServletRequest httpServletRequest);
}
