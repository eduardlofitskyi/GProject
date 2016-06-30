package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;
import com.lofitskyi.service.hibernate.HibernateUserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/change")
public class EditUserController extends HttpServlet{

    @Autowired
    private UserDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String type = req.getParameter("type");

        switch (type) {
            case "edit":
                int id = Integer.valueOf(req.getParameter("id"));
                User user = null;

                try {
                    user = dao.findById((long) id);
                } catch (PersistentException e) {
                    out.println("<html><body onload=\"alert('Something went wrong. Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
                    return;
                }

                req.setAttribute("editUser", user);

                break;
            case "add":
                break;
            default:
                RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
                dispatcher.forward(req, resp);
                break;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("changedata.jsp");
        dispatcher.forward(req, resp);
    }
}
