package com.lofitskyi.repository;

import com.lofitskyi.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void create(User user) throws SQLException, PersistentException;
    void update(User user) throws PersistentException;
    void remove(User user) throws PersistentException;
    List<User> findAll() throws PersistentException;
    User findByLogin(String login) throws PersistentException;
    User findByEmail(String email) throws PersistentException;
}
