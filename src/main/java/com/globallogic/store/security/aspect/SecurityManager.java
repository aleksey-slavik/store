package com.globallogic.store.security.aspect;

import com.globallogic.store.exception.AccessDeniedException;
import com.globallogic.store.security.spring.Authenticateble;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityManager {

    private Authenticateble authenticationFacade;

    public void setAuthenticationFacade(Authenticateble authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("SECURITY: " + SecurityContextHolder.getContext().getAuthentication());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return joinPoint.proceed();
            }
        }

        throw new AccessDeniedException();
    }
}
