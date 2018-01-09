package com.globallogic.store.security.aspect;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

public class SecurityManager {

    private AbstractDAO<User> userDAO;

    public void setUserDAO(AbstractDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    public ModelAndView checkAccess(ProceedingJoinPoint joinPoint) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        if (username != null) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            User user = userDAO.findByCriteria(params).get(0);

            if (user.getRole() == Role.ADMIN) {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    return new ModelAndView("error/403");
                }
            }
        }

        return new ModelAndView("error/403");
    }
}
