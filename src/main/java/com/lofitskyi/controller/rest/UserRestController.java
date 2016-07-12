package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/all")
    public List<User> fetchAll() throws PersistentException {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}")
    public User fetch(@PathVariable Long id) throws PersistentException {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void save(User user) throws PersistentException, SQLException {
        service.create(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void save(@PathVariable Long id, User user) throws PersistentException {
        service.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable Long id) throws PersistentException {
        User user = new User();
        user.setId(id);
        service.update(user);
    }
}
