package com.globallogic.store.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import com.globallogic.store.security.aspect.Access;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class ShowUser extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = httpServletRequest.getParameter("id");

        if (id == null) {
            return getUserList();
        } else {
            return getUserById(Long.valueOf(id));
        }
    }

    private ModelAndView getUserList() {
       /* ModelAndView mav = new ModelAndView();

        try {
            ObjectMapper mapper = new ObjectMapper();
            User[] users = mapper.readValue(new URL("http://localhost:8080/users?all"), User[].class);
            mav.addObject("users", users);
            mav.setViewName("user/list");
        } catch (IOException e) {
            mav.setViewName("error/error");
        }

        return mav;*/

       return new ModelAndView("user/list");
    }

    private ModelAndView getUserById(Long id) {
        ModelAndView mav = new ModelAndView();

        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(new URL("http://localhost:8080/users/" + id), User.class);
            mav.addObject("user", user);
            mav.setViewName("user/item");
        } catch (IOException e) {
            mav.setViewName("error/error");
        }

        return mav;
    }
}
