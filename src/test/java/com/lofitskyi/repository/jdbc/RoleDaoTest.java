package com.lofitskyi.repository.jdbc;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.RoleDao;
import com.lofitskyi.utils.DataSourceTestAdapter;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.h2.engine.Constants.UTF8;


public class RoleDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private IDatabaseTester tester;
    private RoleDao dao;

    @BeforeClass
    public static void startUp() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", UTF8, false);
    }

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("role/role-dataset.xml"));
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    public void shouldFoundRoleByName() throws PersistentException {
        Role role = new JdbcRoleRepository(new DataSourceTestAdapter(tester)).findByName("admin");

        Assert.assertEquals("admin", role.getName());
    }

    @Test
    public void shouldThrowExceptionWhenTryFoundNotPresentedRole() {

        try {
            new JdbcRoleRepository(new DataSourceTestAdapter(tester)).findByName("NINJA");
            Assert.assertTrue("Should throw PersistentException", false);
        } catch (PersistentException e) {
            Assert.assertTrue(e.getCause() instanceof JdbcRoleRepository.NoSuchRoleException);
        }
    }

    @Test
    public void shouldRemoveRowFromTable() throws PersistentException {
        Role role = new Role("admin");
        dao = new JdbcRoleRepository(new DataSourceTestAdapter(tester));

        dao.remove(role);
        try {
            dao.findByName(role.getName());
            Assert.assertTrue("Should throw PersistentException", false);
        } catch (PersistentException e) {
            Assert.assertTrue(e.getCause() instanceof JdbcRoleRepository.NoSuchRoleException);
        }
    }

    @Test
    public void shouldPersistAndFlushOnCreate() throws Exception {
        Role role = new Role("new_role");
        dao = new JdbcRoleRepository(new DataSourceTestAdapter(tester));

        dao.create(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role/role-dataset-save.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "ROLE", ignore);
    }

    @Test
    public void shouldUpdateRow() throws Exception {
        dao = new JdbcRoleRepository(new DataSourceTestAdapter(tester));
        Role role = dao.findByName("admin");
        role.setName("super_admin");

        dao.update(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role/role-dataset-update.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "ROLE", ignore);
    }

}