package com.globallogic.store.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class UpdateUser extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return getUserData(id);
        } else if (method.equals(METHOD_POST)) {
            return updateUserData(id, httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getUserData(Long id) {
        ModelAndView mav = new ModelAndView();
        ObjectMapper mapper = new ObjectMapper();

        try {
            User user = mapper.readValue(new URL("http://localhost:8080/users/" + id), User.class);
            mav.addObject("user", user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("user/update");
        return mav;
    }

    private ModelAndView updateUserData(Long id, HttpServletRequest httpServletRequest) {
        String firstname = httpServletRequest.getParameter("firstname");
        String lastname = httpServletRequest.getParameter("lastname");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");
        Role role = Role.valueOf(httpServletRequest.getParameter("role"));
        User user = new User(firstname, lastname, username, password, email, role);

        ModelAndView mav = new ModelAndView();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        RestTemplate template = new RestTemplate();
        template.exchange("http://localhost:8080/users/" + id, HttpMethod.PUT, request, Void.class);
        mav.setViewName("redirect:/user");
        return mav;
    }
}
