package com.globallogic.store.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.List;

public abstract class ShowTemplate<T> extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = httpServletRequest.getParameter("id");

        try {
            if (id == null) {
                return getList();
            } else {
                return getItemById(Long.valueOf(id));
            }
        } catch (IOException e) {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getList() throws IOException {
        ModelAndView mav = new ModelAndView();
        ObjectMapper mapper = new ObjectMapper();
        List<T> items = mapper.readValue(new URL(getRestPath()), mapper.getTypeFactory().constructCollectionType(List.class, getType()));
        mav.addObject("items", items);
        mav.setViewName(getListItemViewName());
        return mav;
    }

    private ModelAndView getItemById(Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        ObjectMapper mapper = new ObjectMapper();
        T product = mapper.readValue(new URL(getRestPath() + id), getType());
        mav.addObject("item", product);
        mav.setViewName(getSimpleItemViewName());
        return mav;
    }

    private Class<T> getType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract String getRestPath();
    public abstract String getSimpleItemViewName();
    public abstract String getListItemViewName();
}
