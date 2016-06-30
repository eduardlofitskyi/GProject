package com.lofitskyi.service.jdbc;

import com.lofitskyi.config.AbstractDataSource;
import com.lofitskyi.config.PoolDataSource;
import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final String CREATE_SQL = "INSERT INTO USER " +
            "(login, password, email, first_name, last_name, birthday, role_id)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL = "UPDATE USER SET " +
            "login = ?, password = ?, email = ?, first_name = ?, last_name = ?, birthday = ?, role_id = ? " +
            "WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM USER WHERE login = ?";
    private static final String DELETE_BY_ID_SQL= "DELETE FROM USER WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USER";
    private static final String FIND_BY_LOGIN_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE login = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE email = ?";
    private static final String FIND_BY_ID_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE id = ?";

    //TODO make dependency injection
    private AbstractDataSource jdbc = new PoolDataSource();

    public JdbcUserDao() {
    }

    //for injection others DataSource implementation (in my case, for injection DBUnit's tester connection)
    public JdbcUserDao(AbstractDataSource jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(User user) throws PersistentException {

        try (Connection conn = this.jdbc.createConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setDate(6, user.getBirthday());
                stmt.setLong(7, user.getRole().getId());
                stmt.execute();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new PersistentException(e.getMessage(), e);
            }

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) throws PersistentException {
        try (Connection conn = this.jdbc.createConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setDate(6, user.getBirthday());
                stmt.setLong(7, user.getRole().getId());
                stmt.setLong(8, user.getId());
                stmt.execute();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new PersistentException(e.getMessage(), e);
            }

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public void remove(User user) throws PersistentException {
        try (Connection conn = this.jdbc.createConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
                stmt.setString(1, user.getLogin());
                stmt.execute();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new PersistentException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }


    public void removeById(User user) throws PersistentException {
        try (Connection conn = this.jdbc.createConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID_SQL)) {
                stmt.setLong(1, user.getId());
                stmt.execute();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new PersistentException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws PersistentException {

        List<User> users = new ArrayList<>();

        try (Connection conn = this.jdbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL_SQL); ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                Role role = new JdbcRoleDao(this.jdbc).findById(resultSet.getLong("role_id"));

                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setRole(role);

                users.add(user);
            }

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return users;
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        User user = new User();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_LOGIN_SQL)) {
            stmt.setString(1, login);

            user = getUserFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User findById(Long id) throws PersistentException {
        User user = new User();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {
            stmt.setLong(1, id);

            user = getUserFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) throws PersistentException {
        User user = new User();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_EMAIL_SQL)) {
            stmt.setString(1, email);

            user = getUserFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return user;
    }

    private User getUserFromResultSet(ResultSet rs) throws PersistentException {

        User user;

        try (ResultSet resultSet = rs) {

            if (!resultSet.next()) throw new PersistentException(new NoSuchUserException());

            user = new User();
            Role role = new JdbcRoleDao(this.jdbc).findById(resultSet.getLong("role_id"));

            user.setId(resultSet.getLong("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setRole(role);
        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return user;
    }

    public class NoSuchUserException extends RuntimeException {
    }
}
