package com.globallogic.store.controller;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.field.Key;
import com.globallogic.store.field.View;
import com.globallogic.store.model.Product;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for product items
 *
 * @author oleksii.slavik
 */
public class ProductsController extends AbstractController {

    /**
     * Product item controller
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return product item page
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(View.PRODUCT);
    }
}
