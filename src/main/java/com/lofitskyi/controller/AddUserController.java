package com.lofitskyi.controller;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.UserDao;
import com.lofitskyi.repository.hibernate.HibernateUserDao;
import com.lofitskyi.repository.jdbc.JdbcRoleDao;
import com.lofitskyi.repository.jdbc.JdbcUserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;


@WebServlet("/add")
public class AddUserController extends HttpServlet{

    private UserDao dao = new HibernateUserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

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
            if (e.getCause() instanceof JdbcRoleDao.NoSuchRoleException) {
                resp.sendRedirect(req.getHeader("referer"));
                return;
            } else {
                out.println("<html><body onload=\"alert('Something went wrong. Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
                return;
            }
        }

        User newUser = new User(login, password, email, firstName, lastName, birthday, userRole);

        try {
            dao.create(newUser);
        } catch (SQLException | PersistentException e) {
            out.println("<html><body onload=\"alert('Something went wrong. Try again')\"><a href=\"home.jsp\">Home page</a></body></html>");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
        dispatcher.forward(req, resp);
    }
}
