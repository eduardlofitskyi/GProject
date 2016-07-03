package com.lofitskyi.repository;

import com.lofitskyi.entity.Role;

public interface RoleDao {

    void create(Role role) throws PersistentException;
    void update(Role role) throws PersistentException;
    void remove(Role role) throws PersistentException;
    Role findByName(String name) throws PersistentException;
}
