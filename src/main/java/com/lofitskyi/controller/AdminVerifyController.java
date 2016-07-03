package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminVerifyController {

    @Autowired
    private UserService dao;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model){
        try {
            final List<User> users = dao.findAll();
            model.addAttribute("userList", users);
        } catch (PersistentException e) {
            return "error";
        }
        return "admin";
    }
}
