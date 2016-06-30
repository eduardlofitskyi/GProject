package com.lofitskyi.repository.hibernate;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.RoleDao;
import com.lofitskyi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class HibernateRoleDao implements RoleDao {

    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private static final String FIND_BY_NAME_JPQL = "SELECT new Role(id, name) FROM Role WHERE name=:name";

    @Override
    public void create(Role role) throws PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Role role) throws PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void remove(Role role) throws PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.remove(role);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public Role findByName(String name) throws PersistentException {
        Role role;
        try (Session session = sf.openSession()) {
            Query<Role> query = session.getSession().createQuery(FIND_BY_NAME_JPQL);
            query.setParameter("name", name);
            role = query.uniqueResult();

            if (role == null) throw new PersistentException("No such role");
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return role;
    }
}
