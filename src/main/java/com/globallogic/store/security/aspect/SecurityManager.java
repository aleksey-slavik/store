package com.globallogic.store.security.aspect;

import com.globallogic.store.exception.AccessDeniedException;
import com.globallogic.store.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    public void getAuthorityBefore(JoinPoint joinPoint) {
        System.out.println("logBefore() is running!");
        System.out.println("args length : " + joinPoint.getArgs().length);
        System.out.println("id : " + joinPoint.getArgs()[0]);
        System.out.println("user : " + joinPoint.getArgs()[1]);
        user = (User) joinPoint.getArgs()[1];
    }

    public Object checkAuthorizedAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return joinPoint.proceed();
            } else if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
                if (((UserDetails) principal).getUsername().equals(user.getUsername())) {
                    return joinPoint.proceed();
                }
            }
        }

        throw new AccessDeniedException();
    }
}
