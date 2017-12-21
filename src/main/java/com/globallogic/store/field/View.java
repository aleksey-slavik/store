package com.globallogic.store.field;

/**
 * Names of used views
 *
 * @author oleksii.slavik
 */
public class View {

    /**
     * Name of home page view
     */
    public static final String HOME = "home";

    /**
     * Name of login page view
     */
    public static final String LOGIN = "login";

    /**
     * Name of orders page view
     */
    public static final String ORDERS = "orders";

    /**
     * Name of product list page view
     */
    public static final String PRODUCT_LIST = "productList";

    /**
     * Name of product item page view
     */
    public static final String PRODUCT_ITEM = "productItem";

    /**
     * Name of register page view
     */
    public static final String REGISTER = "register";

    public static String redirect(String page) {
        return "redirect:/" + page;
    }
}
