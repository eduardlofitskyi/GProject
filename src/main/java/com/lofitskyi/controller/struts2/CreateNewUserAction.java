package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import com.lofitskyi.util.DateUtil;
import com.opensymphony.xwork2.ActionSupport;

import javax.validation.Valid;

public class CreateNewUserAction extends ActionSupport {

    private UserService service;
    private RoleService roleService;

    @Valid
    private User user;

    private String birthday;

    public CreateNewUserAction(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
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

    @Override
    public String execute() throws Exception {
        user.setBirthday(DateUtil.parseDate(birthday));

        user.setRole(roleService.findByName(user.getRole().getName()));
        service.create(user);

        setUser(null);
        setBirthday(null);

        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }
}
