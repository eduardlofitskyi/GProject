package com.lofitskyi.service.hibernate;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.RoleRepository;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateRoleDao implements RoleDao {

    @Autowired
    private RoleRepository repository;

    @Override
    public void create(Role role) throws PersistentException {
        repository.saveAndFlush(role);
    }

    @Override
    public void update(Role role) throws PersistentException {
        repository.saveAndFlush(role);
    }

    @Override
    public void remove(Role role) throws PersistentException {
        repository.delete(role);
    }

    @Override
    public Role findByName(String name) throws PersistentException {
        Role role = repository.findByName(name);

        if (role == null) {
            throw new PersistentException("No such role");
        }

        return role;
    }
}
