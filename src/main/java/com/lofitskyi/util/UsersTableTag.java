package com.lofitskyi.util;

import com.lofitskyi.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class UsersTableTag extends SimpleTagSupport {

    private List<User> users;

    public void setCollection(List<User> users) {
        this.users = users;
    }

    @Override
    public void doTag() throws JspException, IOException {

        JspWriter out = this.getJspContext().getOut();
        out.println("<table id=\"result-table\"><tr>\n" +
                "    <th>Login</th>\n" +
                "    <th>First Name</th>\n" +
                "    <th>Last Name</th>\n" +
                "    <th>Age</th>\n" +
                "    <th>Role</th>\n" +
                "    <th>Action</th>\n" +
                "   </tr>");


        for (User user: users){
            out.println("<tr>");
            out.println("<td>" + user.getLogin() + "</td>");
            out.println("<td>" + user.getFirstName() + "</td>");
            out.println("<td>" + user.getLastName() + "</td>");
            out.println("<td>" + user.getBirthday() + "</td>");
            out.println("<td>" + user.getRole().getName() + "</td>");
            out.println("<td><a href=\"/change?type=edit&id=" + user.getId() + "&username=" + user.getLogin() + "\">Edit</a> /" +
                    " <a href=\"/del?id=" + user.getId() + "\" onClick=\"return confirm('Are you sure you want to remove user?');\">Remove</a>");
            out.println("</tr>");
        }

        out.println("</table>");
    }
}
