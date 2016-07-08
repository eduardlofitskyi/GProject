package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import com.opensymphony.xwork2.Action;

public class ChangeAction implements Action {

    private UserService service;

    private String type;
    private Long id;
    private User user;
    private String birthday;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public ChangeAction(UserService service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String execute() throws Exception {

        switch (type) {
            case "edit":
                user = service.findById(id);

                id = user.getId();
                login = user.getLogin();
                password = user.getPassword();
                email = user.getEmail();
                firstName = user.getFirstName();
                lastName = user.getLastName();
                birthday = user.getBirthday().toString();
                role = user.getRole().getName();

                user = null;

                return "update";

            case "add":
                return "add";

            default:
                return ERROR;
        }
    }
}
