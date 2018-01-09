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

    public ModelAndView checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("AROUND");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("USERNAME : " + username);

        if (username != null) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            User user = userDAO.findByCriteria(params).get(0);
            System.out.println("USER : " + user);

            if (user.getRole() == Role.ADMIN) {
                return (ModelAndView) joinPoint.proceed();
            }
        }

        return new ModelAndView("error/403");
    }
}
