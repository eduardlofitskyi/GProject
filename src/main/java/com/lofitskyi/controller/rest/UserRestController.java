package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> fetchAll() throws PersistentException {
        return service.findAll();
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public Page<User> fetchAll(@PathVariable Integer id) throws PersistentException {
        return service.findAllPagable(id);
    }

    @RequestMapping(value = "/{id}")
    public User fetch(@PathVariable Long id) throws PersistentException {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User save(@RequestBody User user) throws PersistentException, SQLException {
        Role role = roleService.findByName(user.getRole().getName());
        user.setRole(role);

        return service.create(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void save(@PathVariable Long id, @RequestBody User user) throws PersistentException {
        Role role = roleService.findByName(user.getRole().getName());
        user.setRole(role);

        service.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable Long id) throws PersistentException {
        User user = new User();
        user.setId(id);
        service.remove(user);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong. Try again")
    @ExceptionHandler(PersistentException.class)
    public void exceptionHandler(){
        //NOP
    }
}
