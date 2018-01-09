package com.globallogic.store.security.aspect;

import com.globallogic.store.model.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

   Role role();
}
