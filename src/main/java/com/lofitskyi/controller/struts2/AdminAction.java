package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import com.opensymphony.xwork2.Action;

import java.util.List;

public class AdminAction implements Action {

    private UserService service;
    private List<User> userList;

    public AdminAction(UserService service) {
        this.service = service;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String execute() throws Exception {
        userList = service.findAll();
        return SUCCESS;
    }

}
