package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;
import com.lofitskyi.service.hibernate.HibernateUserDao;
import com.lofitskyi.service.jdbc.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signin")
public class SignInController extends HttpServlet{

    @Autowired
    private UserDao dao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String login = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            final User user = dao.findByLogin(login);

            if (!user.getPassword().equals(password)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);

            synchronized (session) {
                session.setAttribute("user", user);
            }

            if (user.getRole().getName().equals("admin")) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
                dispatcher.forward(req, resp);
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                dispatcher.forward(req, resp);
            }

        } catch (PersistentException e) {
            if (e.getCause() instanceof JdbcUserDao.NoSuchUserException) {
                resp.sendRedirect(req.getHeader("referer"));
                return;
            } else {
                out.println("<html><body onload=\"alert('Something went wrong. May be you input incorrect password! Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
                return;
            }
        }
    }
}
