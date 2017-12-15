package com.globallogic.store.controller;

import com.globallogic.store.dao.ProductDAO;
import com.globallogic.store.field.Key;
import com.globallogic.store.field.View;
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
     * Product item controller
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return product item page
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter(Key.ID));
        ModelAndView mav = new ModelAndView(View.PRODUCT);
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(id);
        mav.addObject(Key.PRODUCT, product);
        return mav;
    }
}
