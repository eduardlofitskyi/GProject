package com.lofitskyi.repository.jdbc;

import com.lofitskyi.config.AbstractJdbcDao;
import com.lofitskyi.config.PoolJdbcDao;
import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.RoleDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDao implements RoleDao{

    private static final String CREATE_SQL = "INSERT INTO ROLES (name) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE ROLES SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM ROLES WHERE id = ?";
    private static final String SELECT_SQL = "SELECT id, name FROM ROLES WHERE name = ?";

    //TODO make dependency injection
    private AbstractJdbcDao jdbc = new PoolJdbcDao();


    public void create(Role role) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)){
            stmt.setString(1, role.getName());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Role role) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)){
            stmt.setString(1, role.getName());
            stmt.setLong(2, role.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Role role) {
        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)){
            stmt.setLong(1, role.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Role findByName(String name) {

        Role role = new Role();

        try (Connection conn = this.jdbc.createConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_SQL)){
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) throw new NoSuchRoleException();

            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    private class NoSuchRoleException extends RuntimeException {
    }
}