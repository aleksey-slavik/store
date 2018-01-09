package com.globallogic.store.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URL;

public abstract class UpdateTemplate<T> extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return get(id);
        } else if (method.equals(METHOD_POST)) {
            return post(id, httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView get(Long id) {
        ModelAndView mav = new ModelAndView();
        ObjectMapper mapper = new ObjectMapper();

        try {
            T item = mapper.readValue(new URL(getRestPath() + id), getType());
            mav.addObject("item", item);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName(getFormViewName());
        return mav;
    }

    private ModelAndView post(Long id, HttpServletRequest httpServletRequest) {
        ModelAndView mav = new ModelAndView();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<T> request = new HttpEntity<T>(getItemFromRequest(httpServletRequest), headers);
        RestTemplate template = new RestTemplate();
        template.exchange(getRestPath() + id, HttpMethod.PUT, request, Void.class);
        mav.setViewName(getSuccessViewName());
        return mav;
    }

    private Class<T> getType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract String getRestPath();
    public abstract String getFormViewName();
    public abstract String getSuccessViewName();
    public abstract T getItemFromRequest(HttpServletRequest httpServletRequest);
}
