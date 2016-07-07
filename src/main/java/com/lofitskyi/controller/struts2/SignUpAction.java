package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import com.lofitskyi.util.DateUtil;
import com.opensymphony.xwork2.ActionSupport;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.struts2.ServletActionContext;

import javax.validation.Valid;

public class SignUpAction extends ActionSupport{

    UserService service;
    RoleService roleService;
    ReCaptcha reCaptcha;

    private String recaptcha_challenge_field;
    private String recaptcha_response_field;

    @Valid
    private User user;
    private String birthday;

    public SignUpAction(UserService service, RoleService roleService, ReCaptcha reCaptcha) {
        this.service = service;
        this.roleService = roleService;
        this.reCaptcha = reCaptcha;
    }

    public String getRecaptcha_challenge_field() {
        return recaptcha_challenge_field;
    }

    public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
        this.recaptcha_challenge_field = recaptcha_challenge_field;
    }

    public String getRecaptcha_response_field() {
        return recaptcha_response_field;
    }

    public void setRecaptcha_response_field(String recaptcha_response_field) {
        this.recaptcha_response_field = recaptcha_response_field;
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
        String remoteAddr = getRemoteAddress();

        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptcha_challenge_field, recaptcha_response_field);

        if (reCaptchaResponse.isValid()){

            user.setBirthday(DateUtil.parseDate(birthday));
            user.setRole(roleService.findByName(user.getRole().getName()));

            service.create(user);

            return SUCCESS;
        }

        return INPUT;
    }

    private String getRemoteAddress() {
        return ServletActionContext.getRequest().getRemoteAddr();
    }

}
