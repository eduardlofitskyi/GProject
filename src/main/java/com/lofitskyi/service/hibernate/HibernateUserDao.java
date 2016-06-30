package com.lofitskyi.service.hibernate;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.UserRepository;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class HibernateUserDao implements UserDao {

    @Autowired
    private UserRepository repository;

    @Override
    public void create(User user) throws SQLException, PersistentException {
        repository.saveAndFlush(user);
    }

    @Override
    public void update(User user) throws PersistentException {
        repository.saveAndFlush(user);
    }

    @Override
    public void remove(User user) throws PersistentException {
        repository.delete(user);
    }


    @Override
    public List<User> findAll() throws PersistentException {
        return repository.findAll();
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        User user = repository.findByLogin(login);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }

    @Override
    public User findByEmail(String email) throws PersistentException {
        User user = repository.findByEmail(email);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }

    @Override
    public User findById(Long id) throws PersistentException {
        User user = repository.findById(id);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }
}
