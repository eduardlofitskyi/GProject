package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.jdbc.JdbcUserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/del")
public class RemoveUserController extends HttpServlet{

    private JdbcUserDao dao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        User user = new User();
        user.setId(id);

        try {
            dao.removeById(user);
        } catch (PersistentException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
        dispatcher.forward(req, resp);
    }
}
