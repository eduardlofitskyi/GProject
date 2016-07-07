package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import com.lofitskyi.util.DateUtil;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserAction extends ActionSupport {

    private UserService service;
    private RoleService roleService;

    private User user;

    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String birthday;
    private String role;

    public UpdateUserAction(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String execute() throws Exception {

        user = new User(id, login, password, email, firstName, lastName, null, null);
        user.setBirthday(DateUtil.parseDate(birthday));
        user.setRole(roleService.findByName(role));

        service.update(user);

        return SUCCESS;
    }

    @Override
    public void validate() {
        if (getPassword().length() < 6 || getPassword().length() > 50) addFieldError("password", "Password must be between 6 - 50 characters length");
        if (getEmail().length() < 4) addFieldError("email", "Too short email");
        if (getFirstName().length() > 50) addFieldError("firstName", "Too long first name");
        if (getLastName().length() > 50) addFieldError("lastName", "Too long first surename");
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }
}
