package com.lofitskyi.controller;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;
import com.lofitskyi.service.hibernate.HibernateUserDao;
import com.lofitskyi.service.jdbc.JdbcRoleDao;
import com.lofitskyi.service.jdbc.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;


@WebServlet("/upd")
public class UpdateUserController extends HttpServlet{

    @Autowired
    private UserDao dao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        Long id = Long.valueOf(req.getParameter("id"));
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("f_name");
        String lastName = req.getParameter("l_name");
        String birthdayStr = req.getParameter("birthday");
        String roleStr = req.getParameter("role");

        final String[] splittedDate = birthdayStr.split("-");
        java.sql.Date birthday = new Date(Integer.valueOf(splittedDate[0]) - 1900, Integer.valueOf(splittedDate[1]) - 1, Integer.valueOf(splittedDate[2]));

        Role userRole = null;
        try {
            userRole = new JdbcRoleDao().findByName(roleStr);
        } catch (PersistentException e) {
            if (e.getCause() instanceof JdbcUserDao.NoSuchUserException) {
                resp.sendRedirect(req.getHeader("referer"));
                return;
            } else {
                out.println("<html><body onload=\"alert('Something went wrong. Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
                return;
            }
        }

        User newUser = new User(id, login, password, email, firstName, lastName, birthday, userRole);

        try {
            dao.update(newUser);
        } catch (PersistentException e) {
            out.println("<html><body onload=\"alert('Something went wrong. Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
        dispatcher.forward(req, resp);
    }
}
