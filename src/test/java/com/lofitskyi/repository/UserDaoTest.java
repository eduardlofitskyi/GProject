package com.lofitskyi.repository;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.jdbc.JdbcRoleDao;
import com.lofitskyi.repository.jdbc.JdbcUserDao;
import com.lofitskyi.utils.JdbcDaoTestAdapter;
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

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.h2.engine.Constants.UTF8;

public class UserDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private IDatabaseTester tester;
    private UserDao dao;

    @BeforeClass
    public static void startUp() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", UTF8, false);
    }

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("user/user-dataset.xml"));
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedEmail(){
        try {
            new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByEmail("not_email");
            Assert.assertTrue("Should throw PersistentException", false);
        } catch (PersistentException e) {
            Assert.assertTrue(e.getCause() instanceof JdbcUserDao.NoSuchUserException);
        }
    }

    @Test
    public void shouldFoundUserByEmail() throws PersistentException {
        User user = new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByEmail("email1");

        Assert.assertEquals("user1", user.getLogin());
    }

    @Test
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedLogin(){
        try {
            new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByLogin("not_login");
            Assert.assertTrue("Should throw PersistentException", false);
        } catch (PersistentException e) {
            Assert.assertTrue(e.getCause() instanceof JdbcUserDao.NoSuchUserException);
        }
    }

    @Test
    public void shouldFoundUserByLogin() throws PersistentException {
        User user = new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByLogin("user2");

        Assert.assertEquals("email2", user.getEmail());
    }

    @Test
    public void shouldRemoveRow() throws PersistentException {
        User user = new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByLogin("user2");

        new JdbcUserDao(new JdbcDaoTestAdapter(tester)).remove(user);

        try {
            new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findByLogin("user2");
            Assert.assertTrue("Should throw PersistentException", false);
        } catch (PersistentException e) {
            Assert.assertTrue(e.getCause() instanceof JdbcUserDao.NoSuchUserException);
        }
    }

    @Test
    public void shouldPersistAndFlushOnCreate() throws Exception {
        dao = new JdbcUserDao(new JdbcDaoTestAdapter(tester));
        Role adminRole = new JdbcRoleDao(new JdbcDaoTestAdapter(tester)).findByName("admin");
        User user = new User("new_user", "new_pass", "new_email", "new_f_name", "new_surname", new Date(95, 0, 1), adminRole);

        dao.create(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user/user-dataset-save.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "USER", ignore);
    }

    @Test
    public void shouldUpdateRow() throws Exception {
        dao = new JdbcUserDao(new JdbcDaoTestAdapter(tester));
        User user = dao.findByLogin("user1");
        user.setLogin("upd_user");

        dao.update(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user/user-dataset-update.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "USER", ignore);
    }

    @Test
    public void shouldFetchAllUsersOnFindAll() throws Exception {
        final List<User> users = new JdbcUserDao(new JdbcDaoTestAdapter(tester)).findAll();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user/user-dataset.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "USER", ignore);
        Assert.assertEquals(expectedData.getTable("USER").getRowCount(), users.size());
    }

}
