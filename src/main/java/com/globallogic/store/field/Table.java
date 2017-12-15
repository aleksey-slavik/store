package com.globallogic.store.field;

/**
 * Field names in store database
 *
 * @author oleksii.slavik
 */
public class Table {

    private class Entity {
        public static final String ID = "id";
    }

    public class User extends Entity {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String EMAIL = "email";
        public static final String ROLE = "role";
    }

    public class Product extends Entity {
        public static final String NAME = "name";
        public static final String BRAND = "brand";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
    }

    public class Order extends Entity {
        public static final String BILL = "bill";
        public static final String USER_ID = "user_id";
        public static final String STATUS = "status";
    }
}
