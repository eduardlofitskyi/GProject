package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.UserDao;
import com.lofitskyi.repository.jdbc.JdbcUserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signin")
public class SignInController extends HttpServlet{

    private UserDao dao = new JdbcUserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            final User user = dao.findByLogin(login);

            if (!user.getPassword().equals(password)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
            }

            HttpSession session = req.getSession(true);

            synchronized (session) {
                session.setAttribute("user", user);
            }

            if (user.getRole().getName().equals("admin")) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
                dispatcher.forward(req, resp);
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                dispatcher.forward(req, resp);
            }

        } catch (PersistentException e) {
            resp.sendRedirect(req.getHeader("referer"));
        }

    }
}
