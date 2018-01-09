package com.globallogic.store.security.aspect;

import com.globallogic.store.model.Role;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.servlet.ModelAndView;

@Aspect
public class SecurityManager {

    @Around("execution(* com.globallogic.store.web.user.ShowUser.handleRequest(..)) && @target(access)")
    public ModelAndView checkAccess(ProceedingJoinPoint joinPoint, Access access) {


        if (access.role().equals(Role.ADMIN)) {
            try {
                return (ModelAndView) joinPoint.proceed();
            } catch (Throwable throwable) {
                return new ModelAndView("error/error");
            }
        } else {
            return new ModelAndView("error/403");
        }
    }
}
