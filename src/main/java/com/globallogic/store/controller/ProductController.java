package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.Product;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for product items
 *
 * @author oleksii.slavik
 */
public class ProductController extends AbstractController {

    /**
     * Key of id value
     */
    private static final String ID_KEY = "id";

    /**
     * Key of product value
     */
    private static final String PRODUCT_KEY = "product";

    /**
     * Name of product item page view
     */
    private static final String PRODUCT_VIEW = "product";

    /**
     * Product item controller
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return product item page
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter(ID_KEY));
        ModelAndView mav = new ModelAndView(PRODUCT_VIEW);
        Product product = CrudManager.getProductById(id);
        mav.addObject(PRODUCT_KEY, product);
        return mav;
    }
}
