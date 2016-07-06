package com.lofitskyi.controller;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    UserService service;

    @Autowired
    RoleService roleService;

    @Autowired
    private ReCaptcha reCaptchaService;

    public UserController() {
    }

    public UserController(UserService service) {
        this.service = service;
    }

    public UserController(UserService service, ReCaptcha reCaptcha) {
        this.service = service;
        this.reCaptchaService = reCaptcha;
    }

    public UserController(UserService service, RoleService roleService, ReCaptcha reCaptcha) {
        this.service = service;
        this.roleService = roleService;
        this.reCaptchaService = reCaptcha;
    }

    @ModelAttribute("user")
    public User getUserObject() {
        return new User();
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public String changeTypeResolver(@RequestParam(value = "type") String type,
                                     @RequestParam(value = "id", required = false) Long id,
                                     Model model) throws PersistentException {
        switch (type) {
            case "edit":
                User user = null;

                user = service.findById(id);


                model.addAttribute("editUser", user);
                return "update";

            case "add":
                return "add";

            default:
                return "redirect:/admin";
        }

    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createUser(@Valid User user, Errors errors) throws PersistentException {

        if (errors.hasErrors()) return "add";

        Role role = roleService.findByName(user.getRole().getName());
        user.setRole(role);

        try {
            service.create(user);
        } catch (SQLException e) {
            return "redirect:/error";
        }

        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public String updateUser(@Valid User user, Errors errors) throws PersistentException {

        if (errors.hasErrors()) return "update";

        Role role = roleService.findByName(user.getRole().getName());
        user.setRole(role);

        service.update(user);

        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public String removeUser(@RequestParam(value = "id") Long id) throws PersistentException {

        User user = new User();
        user.setId(id);

        service.remove(user);


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

        if (reCaptchaResponse.isValid()) {
            Role role = roleService.findByName(user.getRole().getName());
            user.setRole(role);

            service.create(user);
            return "redirect:/index";
        } else {
            return "signup";
        }
    }
}
