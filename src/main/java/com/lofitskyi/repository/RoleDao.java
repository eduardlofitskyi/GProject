package com.lofitskyi.repository;

import com.lofitskyi.entity.Role;

public interface RoleDao {

    void create(Role role);
    void update(Role role);
    void remove(Role role);
    Role findByName(String name);
}
