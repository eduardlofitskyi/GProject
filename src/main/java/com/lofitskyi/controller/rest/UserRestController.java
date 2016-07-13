package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> fetchAll() throws PersistentException {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}")
    public User fetch(@PathVariable Long id) throws PersistentException {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void save(@RequestBody User user) throws PersistentException, SQLException {
        service.create(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void save(@PathVariable Long id, @RequestBody User user) throws PersistentException {
        service.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable Long id) throws PersistentException {
        User user = new User();
        user.setId(id);
        service.update(user);
    }
}
