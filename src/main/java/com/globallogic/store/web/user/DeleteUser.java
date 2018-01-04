package com.globallogic.store.web.user;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        ModelAndView mav = new ModelAndView();
        RestTemplate template = new RestTemplate();
        template.delete("http://localhost:8080/users/" + id);
        mav.setViewName("redirect:/user");
        return mav;
    }
}
