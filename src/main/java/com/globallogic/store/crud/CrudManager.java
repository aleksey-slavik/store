package com.globallogic.store.crud;

import com.globallogic.store.exception.SameUserFoundException;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;

import javax.persistence.NoResultException;

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
     * Update given user data
     *
     * @param id        id
     * @param firstname firstname
     * @param lastname  lastname
     * @param username  username
     * @param password  password
     * @param email     email
     */
    public static void updateUser(Long id, String firstname, String lastname, String username, String password, String email) {
        UserCrud.updateUser(id, firstname, lastname, username, password, email);
    }

    /**
     * Delete given user account from database
     *
     * @param id user id
     */
    public static void deleteUser(Long id) {
        UserCrud.deleteUser(id);
    }
}
