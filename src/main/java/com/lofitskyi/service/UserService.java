package com.lofitskyi.service;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User create(User user) throws SQLException, PersistentException;
    void update(User user) throws PersistentException;
    void remove(User user) throws PersistentException;

    List<User> findAll() throws PersistentException;
    User findByLogin(String login) throws PersistentException;
    User findByEmail(String email) throws PersistentException;
    User findById(Long id) throws PersistentException;
}
