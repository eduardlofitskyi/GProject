package com.lofitskyi.controller.ws;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.sql.SQLException;

@Component
@WebService(endpointInterface = "com.lofitskyi.controller.ws.UserResource")
public class UserResourceImpl implements UserResource{

    @Autowired
    UserService service;

    @Override
    public User[] findAll() throws PersistentException {
        return service.findAll().toArray(new User[1]);
    }

    @Override
    public User findById(Long id) throws PersistentException {
        return service.findById(id);
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        return service.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) throws PersistentException {
        return service.findByEmail(email);
    }

    @Override
    public void create(User user) throws SQLException, PersistentException {
        service.create(user);
    }

    @Override
    public void update(User user) throws PersistentException {
        service.update(user);
    }

    @Override
    public void remove(User user) throws PersistentException {
        service.remove(user);
    }
}
