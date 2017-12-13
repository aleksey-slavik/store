package com.globallogic.store.crud;

import com.globallogic.store.exception.SameUserFoundException;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.Product;
import com.globallogic.store.model.User;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * CRUD operations related with all tables.
 *
 * @author oleksii.slavik
 */
public class CrudManager {

    /**
     * Add new user if it already not exist in database
     *
     * @param user user data
     * @return id of new record in database
     */
    public static Long registerUser(User user) throws SameUserFoundException {
        return UserCrud.registerUser(user);
    }

    /**
     * Check that logined user already exist in database
     *
     * @param login login data
     * @return user data
     */
    public static User verifyUser(Login login) throws NoResultException {
        return UserCrud.verifyUser(login);
    }

    /**
     * Return list of all products
     *
     * @return list of all products
     */
    public static List<Product> getProductList() {
       return ProductCrud.getProductList();
    }

    /**
     * Return product by given id
     *
     * @param id given id
     * @return product by given id
     */
    public static Product getProductById(Long id) {
        return ProductCrud.getProductById(id);
    }
}
