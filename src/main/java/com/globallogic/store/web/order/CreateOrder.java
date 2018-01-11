package com.globallogic.store.web.order;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.Order;
import com.globallogic.store.model.OrderItem;
import com.globallogic.store.model.Product;
import com.globallogic.store.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class CreateOrder extends AbstractController {

    /**
     * {@link User} DAO object for access to database.
     */
    private AbstractDAO<User> userDao;

    /**
     * Injection {@link User} DAO object for access to database.
     */
    public void setUserDao(AbstractDAO<User> userDao) {
        this.userDao = userDao;
    }

    /**
     * {@link Product} DAO object for access to database.
     */
    private AbstractDAO<Product> productDao;

    /**
     * Injection {@link Product} DAO object for access to database.
     */
    public void setProductDao(AbstractDAO<Product> productDao) {
        this.productDao = productDao;
    }

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    private AbstractDAO<Order> orderDao;

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    public void setOrderDao(AbstractDAO<Order> orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return getCreateForm();
        } else if (method.equals(METHOD_POST)) {
            return createOrder(httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getCreateForm() {
        ModelAndView mav = new ModelAndView();
        List<User> users = userDao.findAll();
        List<Product> products = productDao.findAll();
        mav.setViewName("order/create");
        mav.addObject("users", users);
        mav.addObject("products", products);
        return new ModelAndView();
    }

    private ModelAndView createOrder(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String name = httpServletRequest.getParameter("name");
        int quantity = Integer.valueOf(httpServletRequest.getParameter("quantity"));

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        User user = userDao.findByCriteria(params).get(0);

        params = new HashMap<String, String>();
        params.put("name", name);
        Product product = productDao.findByCriteria(params).get(0);

        params = new HashMap<String, String>();
        params.put("user_id", user.getId().toString());
        Order order = orderDao.findByCriteria(params).get(0);

        if (order == null) {
            //todo create order
        }

        OrderItem orderItem = new OrderItem();

        ModelAndView mav = new ModelAndView();
        HttpEntity<OrderItem> request = new HttpEntity<OrderItem>(orderItem);
        RestTemplate template = new RestTemplate();
        template.postForEntity("http://localhost:8080/users/", request, User.class);
        mav.setViewName("redirect:/user");
        return mav;
    }
}
