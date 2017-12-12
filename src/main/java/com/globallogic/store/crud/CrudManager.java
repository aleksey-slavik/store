package com.globallogic.store.crud;

import com.globallogic.store.exception.SameUserFoundException;
import com.globallogic.store.exception.UserNotFoundException;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;

import java.util.List;

/**
 * CRUD operations related with all tables.
 *
 * @author oleksii.slavik
 */
public class CrudManager {

    /**
     * Return list of all users
     *
     * @return list of all users
     */
    public static List<User> getUserList() {
        return UserCrud.getUserList();
    }

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
    public static User verifyUser(Login login) throws UserNotFoundException {
        return UserCrud.verifyUser(login);
    }

    /**
     * Update given user data
     *
     * @param user given user data
     */
    public static void updateUser(User user) {
        UserCrud.updateUser(user);
    }

    /**
     * Delete given user account from database
     *
     * @param user given user
     */
    public static void deleteUser(User user) {
        UserCrud.deleteUser(user);
    }
}
