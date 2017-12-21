package com.globallogic.store.controller;

import com.globallogic.store.field.View;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for product item page
 *
 * @author oleksii.slavik
 */
public class ProductItemController extends AbstractController{

    /**
     * Product item controller
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return product item page
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(View.PRODUCT_ITEM);
    }
}
