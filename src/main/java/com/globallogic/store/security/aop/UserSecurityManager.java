package com.globallogic.store.security.aop;

import com.globallogic.store.domain.user.User;
import com.globallogic.store.exception.AccessDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Aspect
public class UserSecurityManager {

    @Around("bean(userDao)")
    public Object checkAuthorizedAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = getAuthentication().getPrincipal();

       /* if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            //admin can do anything
            if (containsAuthority(userDetails, Role.ADMIN)) {
                return joinPoint.proceed();
            }

            User user = getUserFromArgs(joinPoint);

            //customer can change only own data
            if (containsAuthority(userDetails, Role.CUSTOMER) && user != null && userDetails.getUsername().equals(user.getUsername())) {
                return joinPoint.proceed();
            }
        }*/

        throw new AccessDeniedException();
    }

    @AfterReturning(value = "bean(userDao)", returning = "result")
    public void checkAccessToResultData(Object result) {
        Object principal = getAuthentication().getPrincipal();

        /*if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            //admin have access to all data
            if (containsAuthority(userDetails, Role.ADMIN)) {
                return;
            }

            //customer have access only to own data
            if (containsAuthority(userDetails, Role.CUSTOMER) && userDetails.getUsername().equals(((User) result).getUsername())) {
                return;
            }
        }*/

        throw new AccessDeniedException();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /*private boolean containsAuthority(UserDetails userDetails, Role role) {
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return userDetails.getAuthorities().contains(authority);
    }

    private User getUserFromArgs(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof User) {
                return (User) arg;
            }
        }

        return null;
    }*/
}
