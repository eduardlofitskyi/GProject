package com.lofitskyi.repository.jdbc;

import com.lofitskyi.config.AbstractJdbcDao;
import com.lofitskyi.config.PoolJdbcDao;
import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao{

    private static final String CREATE_SQL = "INSERT INTO USERS " +
            "(login, password, email, first_name, last_name, birthday, role_id)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL = "UPDATE USERS SET " +
            "login = ?, password = ?, email = ?, first_name = ?, last_name = ?, birthday = ?, role_id = ? " +
            "WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM USERS WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USERS";
    private static final String FIND_BY_LOGIN_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USERS WHERE login = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT id, login, password, email, first_name, last_name, birthday, role_id FROM USERS WHERE email = ?";

    //TODO make dependency injection
    private AbstractJdbcDao jdbc = new PoolJdbcDao();

    public JdbcUserDao() {
    }

    public JdbcUserDao(AbstractJdbcDao jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(User user) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)){
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setDate(6, user.getBirthday());
            stmt.setLong(7, user.getRole().getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)){
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setDate(6, user.getBirthday());
            stmt.setLong(7, user.getRole().getId());
            stmt.setLong(8, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(User user) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)){
            stmt.setLong(1, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection conn = this.jdbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL_SQL); ResultSet resultSet = stmt.executeQuery()){

            while (resultSet.next()){
                User user = new User();
                Role role = new JdbcRoleDao().findById(resultSet.getLong("role_id"));

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
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User findByLogin(String login) {
        User user = new User();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_LOGIN_SQL)){
            stmt.setString(1, login);

            user = getUserFromResultSet(stmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = new User();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_EMAIL_SQL)){
            stmt.setString(1, email);

            user = getUserFromResultSet(stmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUserFromResultSet(PreparedStatement stmt) throws SQLException {

        User user;

        try(ResultSet resultSet = stmt.executeQuery()){

            if (!resultSet.next()) throw new NoSuchUserException();

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
        }

        return user;
    }

    public class NoSuchUserException extends RuntimeException {
    }
}
