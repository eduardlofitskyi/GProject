package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;
import com.lofitskyi.service.hibernate.HibernateUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class AdminVerifyController {

    @Autowired
    private UserDao dao;

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
