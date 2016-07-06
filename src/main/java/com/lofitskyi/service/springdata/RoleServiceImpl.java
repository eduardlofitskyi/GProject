package com.lofitskyi.service.springdata;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.springdata.RoleRepository;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    public RoleServiceImpl() {
    }

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void create(Role role) throws PersistentException {
        repository.saveAndFlush(role);
    }

    @Override
    @Transactional
    public void update(Role role) throws PersistentException {
        repository.saveAndFlush(role);
    }

    @Override
    @Transactional
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
