package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserRestController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/all")
    public List<User> getAll() throws PersistentException {
        return service.findAll();
    }
}
