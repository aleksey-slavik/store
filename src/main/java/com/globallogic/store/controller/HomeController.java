package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.Product;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller for home page
 *
 * @author oleksii.slavik
 */
public class HomeController extends AbstractController {

    /**
     * Name of home page view
     */
    private static final String HOME_VIEW = "home";

    /**
     * Key of products list for home page
     */
    private static final String PRODUCTS_KEY = "products";

    /**
     * Redirect to home page
     *
     * @param httpServletRequest  page request
     * @param httpServletResponse page response
     * @return home page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView home = new ModelAndView(HOME_VIEW);
        List<Product> products = CrudManager.getProductList();
        home.addObject(PRODUCTS_KEY, products);
        return home;
    }
}
