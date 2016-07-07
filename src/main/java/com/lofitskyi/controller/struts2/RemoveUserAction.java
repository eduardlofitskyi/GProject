package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class RemoveUserAction extends ActionSupport {

    private UserService service;

    private Long id;
    private User user;

    public RemoveUserAction(UserService service) {
        this.service = service;
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

    @Override
    public String execute() throws Exception {
        user = new User();
        user.setId(id);

        service.remove(user);

        return SUCCESS;
    }
}
