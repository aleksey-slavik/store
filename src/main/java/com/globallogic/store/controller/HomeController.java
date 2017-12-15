package com.globallogic.store.controller;

import com.globallogic.store.dao.ProductDAO;
import com.globallogic.store.field.Key;
import com.globallogic.store.field.View;
import com.globallogic.store.model.Product;
import com.globallogic.store.service.AbstractService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

        //ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
        //AbstractService abstractService = (AbstractService) context.getBean("productDAO");

        AbstractService<Product, Long> productDAO = new AbstractService<Product, Long>();
        productDAO.setAbstractDao(new ProductDAO());
        List<Product> products = productDAO.findAll();
        mav.addObject(Key.PRODUCTS, products);
        return mav;
    }
}
