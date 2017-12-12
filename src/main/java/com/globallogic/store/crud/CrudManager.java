package com.globallogic.store.crud;

import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import java.util.List;

public class CrudManager {

    public static List<User> getUserList() {
        return UserCrud.getUserList();
    }

    public static void registerUser(User user) {
        UserCrud.registerUser(user);
    }

    public static User verifyUser(Login login) {
        return UserCrud.verifyUser(login);
    }

    public static void updateUser(User user) {
        UserCrud.updateUser(user);
    }

    public static void deleteUser(User user) {
        UserCrud.deleteUser(user);
    }
}
