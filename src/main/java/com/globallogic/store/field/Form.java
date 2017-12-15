package com.globallogic.store.field;

/**
 * Field names in forms of web-pages
 *
 * @author oleksii.slavik
 */
public class Form {

    /**
     * Name of fields in register form
     */
    public class Register {
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CONFIRM_PASSWORD = "confirm-password";
        public static final String EMAIL = "email";
    }

    /**
     * Name of fields in login form
     */
    public class Login {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
    }
}
