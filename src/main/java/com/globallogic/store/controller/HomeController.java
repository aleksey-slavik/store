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
import java.util.List;

/**
 * Controller for home page
 *
 * @author oleksii.slavik
 */
public class HomeController extends AbstractController {

    /**
     * Redirect to home page
     *
     * @param httpServletRequest  page request
     * @param httpServletResponse page response
     * @return home page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mav = new ModelAndView(View.HOME);

        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        AbstractGenericDAO<Product> productDao = (AbstractGenericDAO<Product>) applicationContext.getBean("productDao");

        List<Product> products = productDao.findAll();
        mav.addObject(Key.PRODUCTS, products);
        return mav;
    }
}
