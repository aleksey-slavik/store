package com.globallogic.store.web.user;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUser extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return getCreateForm();
        } else if (method.equals(METHOD_POST)) {
            return createUser(httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getCreateForm() {
        return new ModelAndView("user/create");
    }

    private ModelAndView createUser(HttpServletRequest httpServletRequest) {
        String firstname = httpServletRequest.getParameter("firstname");
        String lastname = httpServletRequest.getParameter("lastname");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");
        Role role = Role.valueOf(httpServletRequest.getParameter("role"));
        User user = new User(firstname, lastname, username, password, email, role);

        ModelAndView mav = new ModelAndView();
        HttpEntity<User> request = new HttpEntity<User>(user);
        RestTemplate template = new RestTemplate();
        template.postForEntity("http://localhost:8080/users/", request, User.class);
        mav.setViewName("redirect:/user");
        return mav;
    }
}
