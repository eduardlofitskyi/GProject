package com.lofitskyi.repository.jdbc;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcRoleRepository implements RoleDao {

    private static final String CREATE_SQL = "INSERT INTO ROLE (name) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE ROLE SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM ROLE WHERE name = ?";
    private static final String FIND_BY_NAME_SQL = "SELECT id, name FROM ROLE WHERE name = ?";
    private static final String FIND_BY_ID_SQL = "SELECT id, name FROM ROLE WHERE id = ?";

    @Autowired
    private DataSource ds;

    public JdbcRoleRepository() {
    }

    //for injection others DataSource implementation (in my case, for injection DBUnit's tester connection)
    public JdbcRoleRepository(DataSource ds) {
        this.ds = ds;
    }

    public void create(Role role) throws PersistentException {
        try (Connection conn = this.ds.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {
                stmt.setString(1, role.getName());
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

    public void update(Role role) throws PersistentException {
        try (Connection conn = this.ds.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, role.getName());
                stmt.setLong(2, role.getId());
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

    public void remove(Role role) throws PersistentException {
        try (Connection conn = this.ds.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
                stmt.setString(1, role.getName());
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

    public Role findByName(String name) throws PersistentException {

        Role role = null;

        try (Connection conn = this.ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_NAME_SQL)) {
            stmt.setString(1, name);

            role = getRoleFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return role;
    }

    public Role findById(Long id) throws PersistentException {

        Role role = null;

        try (Connection conn = this.ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {
            stmt.setLong(1, id);

            role = getRoleFromResultSet(stmt.executeQuery());

        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return role;
    }

    private Role getRoleFromResultSet(ResultSet rs) throws PersistentException {

        Role role = new Role();

        try (ResultSet resultSet = rs) {
            if (!resultSet.next()) throw new PersistentException(new NoSuchRoleException());

            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            throw new PersistentException(e.getMessage(), e);
        }

        return role;
    }

    public class NoSuchRoleException extends RuntimeException {
    }
}
