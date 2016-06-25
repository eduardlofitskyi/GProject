package com.lofitskyi.repository;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.jdbc.JdbcUserDao;
import com.lofitskyi.utils.JdbcDaoAdapter;
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

public class UserDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private IDatabaseTester tester;
    private UserDao dao;

    @BeforeClass
    public static void startUp() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "schema.sql", UTF8, false);
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

    @Test(expected = JdbcUserDao.NoSuchUserException.class)
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedEmail(){
        new JdbcUserDao(new JdbcDaoAdapter(tester)).findByEmail("not_email");
    }

    @Test
    public void shouldFoundUserByEmail(){
        User user = new JdbcUserDao(new JdbcDaoAdapter(tester)).findByEmail("email1");

        Assert.assertEquals("user1", user.getLogin());
    }

    @Test(expected = JdbcUserDao.NoSuchUserException.class)
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedLogin(){
        new JdbcUserDao(new JdbcDaoAdapter(tester)).findByLogin("not_login");
    }

    @Test
    public void shouldFoundUserByLogin(){
        User user = new JdbcUserDao(new JdbcDaoAdapter(tester)).findByLogin("user2");

        Assert.assertEquals("email2", user.getEmail());
    }

}
