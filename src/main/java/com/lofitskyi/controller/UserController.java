package com.lofitskyi.controller;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class UserController {

    @Autowired
    UserService dao;

    @Autowired
    RoleService roleService;

    @Autowired
    private ReCaptcha reCaptchaService;

    @ModelAttribute("user")
    public User getUserObject() {
        return new User();
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public String changeTypeResolver(@RequestParam(value = "type") String type,
                             @RequestParam(value = "id", required = false) Long id,
                             Model model){
        switch (type){
            case "edit":
                User user = null;
                try {
                    user = dao.findById(id);
                } catch (PersistentException e) {
                    return "error";
                }

                model.addAttribute("editUser", user);
                return "update";

            case "add":
                return "add";

            default:
                return "redirect:/admin";
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createUser(@Valid User user, Errors errors){

        if (errors.hasErrors()) return "add";

        try {
            dao.create(user);
        } catch (PersistentException | SQLException e) {
            return "error";
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public String updateUser(@Valid User user, Errors errors){

        if (errors.hasErrors()) return "update";

        try {
            dao.update(user);
        } catch (PersistentException e) {
            return "error";
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public String removeUser(@RequestParam(value = "id") Long id){

        User user = new User();
        user.setId(id);
        try {
            dao.remove(user);
        } catch (PersistentException e) {
            return "error";
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(ServletRequest request,
                         @Valid User user,
                         Errors errors) throws SQLException, PersistentException {

        if (errors.hasErrors()) return "signup";

        String challenge = request.getParameter("recaptcha_challenge_field");
        String response = request.getParameter("recaptcha_response_field");
        String remoteAddr = request.getRemoteAddr();

        ReCaptchaResponse reCaptchaResponse = reCaptchaService.checkAnswer(remoteAddr, challenge, response);

        if(reCaptchaResponse.isValid()) {
            dao.create(user);
            return "redirect:/index";
        } else {
            return "signup";
        }
    }
}
