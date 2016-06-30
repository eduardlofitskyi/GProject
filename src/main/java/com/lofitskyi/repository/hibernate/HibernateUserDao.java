package com.lofitskyi.repository.hibernate;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.UserDao;
import com.lofitskyi.repository.jdbc.JdbcUserDao;
import com.lofitskyi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class HibernateUserDao implements UserDao {

    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private static final String FIND_ALL_JPQL = "FROM User";
    private static final String FIND_BY_LOGIN_JPQL = "FROM User WHERE login=:login";
    private static final String FIND_BY_EMAIL_JPQL = "FROM User WHERE email=:email";
    private static final String FIND_BY_ID_JPQL = "FROM User WHERE id=:id";


    @Override
    public void create(User user) throws SQLException, PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) throws PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void remove(User user) throws PersistentException {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void removeById(User user) throws PersistentException {
        try {
            this.remove(user);
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws PersistentException {
        List<User> users;
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(FIND_ALL_JPQL);
            users = query.list();
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return users;
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        User user;
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(FIND_BY_LOGIN_JPQL);
            query.setParameter("login", login);
            user = query.uniqueResult();

            if (user == null) throw new PersistentException("No such user");
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws PersistentException {
        User user;
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(FIND_BY_EMAIL_JPQL);
            query.setParameter("email", email);
            user = query.uniqueResult();

            if (user == null) throw new PersistentException("No such user");
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User findById(Long id) throws PersistentException {
        User user;
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(FIND_BY_ID_JPQL);
            query.setParameter("id", id);
            user = query.uniqueResult();

            if (user == null) throw new PersistentException("No such user");
        } catch (Exception e) {
            throw new PersistentException(e.getMessage(), e);
        }
        return user;
    }
}
