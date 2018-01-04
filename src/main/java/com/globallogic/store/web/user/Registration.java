package com.globallogic.store.web.user;

import com.globallogic.store.model.User;
import com.globallogic.store.security.RegisterUserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return getRegisterForm();
        } else if (method.equals(METHOD_POST)) {
            return registerUser(httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getRegisterForm() {
        return new ModelAndView("user/register");
    }

    private ModelAndView registerUser(HttpServletRequest httpServletRequest) {
        String firstname = httpServletRequest.getParameter("firstname");
        String lastname = httpServletRequest.getParameter("lastname");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");
        User user = new User(firstname, lastname, username, password, email);

        ModelAndView mav = new ModelAndView();
        HttpEntity<User> request = new HttpEntity<User>(user);
        RestTemplate template = new RestTemplate();
        template.postForEntity("http://localhost:8080/users/", request, User.class);

        RegisterUserService userService = new RegisterUserService();
        userService.autoLogin(username, password);

        mav.setViewName("redirect:/home");
        return mav;
    }
}
