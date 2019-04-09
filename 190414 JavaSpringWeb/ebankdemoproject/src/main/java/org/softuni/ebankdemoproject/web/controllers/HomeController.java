package org.softuni.ebankdemoproject.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView home(HttpSession session, Authentication principal, ModelAndView modelAndView) {
        modelAndView.addObject("user", principal.getName());
        modelAndView.setViewName("home");

        return modelAndView;
    }
}
