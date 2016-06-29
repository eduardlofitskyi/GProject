package com.lofitskyi.repository.hibernate;

import com.lofitskyi.config.AbstractDataSource;
import com.lofitskyi.config.PoolDataSource;
import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.RoleDao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

public class JpaRoleDao implements RoleDao{

    private AbstractDataSource dataSource = new PoolDataSource();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");

    @Override
    public void create(Role role) throws PersistentException {

    }

    @Override
    public void update(Role role) throws PersistentException {

    }

    @Override
    public void remove(Role role) throws PersistentException {

    }

    @Override
    public Role findByName(String name) throws PersistentException {
        return null;
    }
}
