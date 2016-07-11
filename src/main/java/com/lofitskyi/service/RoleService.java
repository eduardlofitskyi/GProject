package com.lofitskyi.service;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;

public interface RoleService {

    void create(Role role) throws PersistentException;
    void update(Role role) throws PersistentException;
    void remove(Role role) throws PersistentException;
    Role findByName(String name) throws PersistentException;
}
