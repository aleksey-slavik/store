package com.globallogic.store.security.aspect;

import com.globallogic.store.exception.AccessDeniedException;
import com.globallogic.store.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class SecurityManager {

    private Authenticateble authenticationFacade;

    public void setAuthenticationFacade(Authenticateble authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    public Object checkAdminAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return joinPoint.proceed();
            }
        }

        throw new AccessDeniedException();
    }

    private User user;

    public void getUserDataBefore(JoinPoint joinPoint) {
        user = (User) joinPoint.getArgs()[1];
    }

    public Object checkAuthorizedAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return joinPoint.proceed();
            } else if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER")) && user != null) {
                if (((UserDetails) principal).getUsername().equals(user.getUsername())) {
                    return joinPoint.proceed();
                }
            }
        }

        throw new AccessDeniedException();
    }

    public void checkAccessAfter(JoinPoint joinPoint, Object result) throws Throwable {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        System.out.println(((UserDetails) principal).getUsername());

        if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return;
        }

        String username = null;

        if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            if (result instanceof List) {
                if (((List) result).size() == 1) {
                    username = ((User) ((List) result).get(0)).getUsername();
                }
            } else {
                username = ((User) result).getUsername();
            }
        }

        if (username == null || !username.equals(((UserDetails) principal).getUsername())) {
            throw new AccessDeniedException();
        }

    }
}
