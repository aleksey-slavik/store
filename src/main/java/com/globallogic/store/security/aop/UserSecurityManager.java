package com.globallogic.store.security.aop;

import com.globallogic.store.domain.user.AuthorityName;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.exception.AccessDeniedException;
import com.globallogic.store.security.core.AuthenticatedUser;
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

/**
 * User AOP security manager
 *
 * @author oleksii.slavik
 */
@Aspect
public class UserSecurityManager {

    /**
     * Check authorized access to own data
     *
     * @param joinPoint aop join point
     * @return object
     * @throws AccessDeniedException throws when user don't have access to object
     */
    @Around("bean(userDao)")
    public Object checkAuthorizedAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            AuthenticatedUser userDetails = (AuthenticatedUser) principal;

            //admin can do anything
            if (containsAuthority(userDetails, AuthorityName.ADMIN)) {
                return joinPoint.proceed();
            }

            User user = getUserFromArgs(joinPoint);

            //customer can change only own data
            if (containsAuthority(userDetails, AuthorityName.CUSTOMER) && user != null && userDetails.getUsername().equals(user.getUsername())) {
                return joinPoint.proceed();
            }
        }

        throw new AccessDeniedException();
    }

    /**
     * Check access to returned data
     *
     * @param result returned data
     * @throws AccessDeniedException throws when user don't have access to returned data
     */
    @AfterReturning(value = "bean(userDao)", returning = "result")
    public void checkAccessToResultData(Object result) throws AccessDeniedException {
        Object principal = getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            //admin have access to all data
            if (containsAuthority(userDetails, AuthorityName.ADMIN)) {
                return;
            }

            //customer have access only to own data
            if (containsAuthority(userDetails, AuthorityName.CUSTOMER) && userDetails.getUsername().equals(((User) result).getUsername())) {
                return;
            }
        }

        throw new AccessDeniedException();
    }

    /**
     * Get current authentication from security context
     *
     * @see Authentication
     * @see SecurityContextHolder
     * @return current authentication
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Check contains needed authority in given user
     *
     * @param userDetails given user
     * @param role needed authority
     * @return true, if given user has needed authority, false in otherwise
     */
    private boolean containsAuthority(UserDetails userDetails, AuthorityName role) {
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return userDetails.getAuthorities().contains(authority);
    }

    /**
     * Get user data from arguments
     *
     * @see User
     * @param joinPoint aop join point
     * @return user data if it consist in method arguments, null in otherwise
     */
    private User getUserFromArgs(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof User) {
                return (User) arg;
            }
        }

        return null;
    }
}
