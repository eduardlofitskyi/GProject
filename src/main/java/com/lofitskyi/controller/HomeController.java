package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @ModelAttribute("user")
    public User getUserObject() {
        return new User();
    }

    @RequestMapping("*")
    public String hello(HttpServletRequest request) {
        return "home";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexDefault(){
        return "home";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/signupform", method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }
}
