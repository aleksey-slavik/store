package com.globallogic.store.security.aspect;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

public class SecurityManager {

    public ModelAndView checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(principal.toString());

        if (principal instanceof UserDetails) {
            System.out.println(((UserDetails) principal).getUsername());
            System.out.println(((UserDetails) principal).getAuthorities());

            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {

                return (ModelAndView) joinPoint.proceed();
            }
        }

        return new ModelAndView("error/403");
    }
}
