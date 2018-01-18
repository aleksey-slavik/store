package com.globallogic.store.security.aspect;

import com.globallogic.store.exception.AccessDeniedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityManager {

    private Authenticateble authenticationFacade;

    public void setAuthenticationFacade(Authenticateble authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            if (((UserDetails) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return joinPoint.proceed();
            }
        }

        throw new AccessDeniedException();
    }
}
