package com.lofitskyi.repository;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.jdbc.JdbcRoleDao;
import com.lofitskyi.utils.JdbcDaoAdapter;
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
        RunScript.execute(JDBC_URL, USER, PASSWORD, "schema.sql", UTF8, false);
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
    public void shouldFoundRoleByName(){
        Role role = new JdbcRoleDao(new JdbcDaoAdapter(tester)).findByName("admin");

        Assert.assertEquals("admin", role.getName());
    }

    @Test(expected = JdbcRoleDao.NoSuchRoleException.class)
    public void shouldThrowExceptionWhenTryFoundNotPresentedRole(){
        new JdbcRoleDao(new JdbcDaoAdapter(tester)).findByName("NINJA");
    }

    @Test(expected = JdbcRoleDao.NoSuchRoleException.class)
    public void shouldRemoveRowFromTable(){
        Role role = new Role("admin");
        dao = new JdbcRoleDao(new JdbcDaoAdapter(tester));

        dao.remove(role);
        dao.findByName(role.getName());
    }

    @Test
    public void shouldPersistAndFlushOnCreate() throws Exception {
        Role role = new Role("new_role");
        dao = new JdbcRoleDao(new JdbcDaoAdapter(tester));

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
        dao = new JdbcRoleDao(new JdbcDaoAdapter(tester));
        Role role = dao.findByName("admin");
        role.setName("super_admin");

        dao.update(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("role/role-dataset-update.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        System.out.println(dao.findByName("guest").getId());

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "ROLE", ignore);
    }

}