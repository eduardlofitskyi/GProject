package com.lofitskyi.repository.hibernate;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.UserDao;
import com.lofitskyi.utils.DataSourceTestAdapter;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;

import java.sql.Date;
import java.util.List;

import static org.h2.engine.Constants.UTF8;
import static org.junit.Assert.*;

public class HibernateUserDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test_hibernate;DB_CLOSE_DELAY=-1";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static SessionFactory sf;

    private UserDao dao;
    private IDatabaseTester tester;


    @BeforeClass
    public static void startUp() throws Exception {
        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Role.class)
            .addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test_hibernate;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", UTF8, false);

        sf = configuration.buildSessionFactory();
    }

    @Before
    public void setUp() throws Exception {
        dao = new HibernateUserDao(sf);

        tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("user/user-dataset.xml"));
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test(expected = PersistentException.class)
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedEmail() throws PersistentException {
            dao.findByEmail("not_email");
    }

    @Test
    public void shouldFoundUserByEmail() throws PersistentException {
        User user = dao.findByEmail("email1@mail.com");

        Assert.assertEquals("user1", user.getLogin());
    }

    @Test(expected = PersistentException.class)
    public void shouldThrowExceptionWhenTryFoundUserWithNotPresentedLogin() throws PersistentException {
            dao.findByLogin("not_login");
    }

    @Test
    public void shouldFoundUserByLogin() throws PersistentException {
        User user = dao.findByLogin("user2");

        Assert.assertEquals("email2@mail.com", user.getEmail());
    }

    @Test(expected = PersistentException.class)
    public void shouldRemoveRow() throws PersistentException {
        User user = dao.findByLogin("user2");

        dao.remove(user);

        dao.findByLogin("user2");
    }

    @Test
    public void shouldPersistAndFlushOnCreate() throws Exception {
        Role adminRole = new HibernateRoleDao(sf).findByName("admin");
        User user = new User("new_user", "new_pass", "new_email@mail.com", "new_f_name", "new_surname", new Date(95, 0, 1), adminRole);

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
        User user = dao.findByLogin("user1");
        user.setLogin("upd_user");
        user.setPassword("password_upd");

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
        final List<User> users = dao.findAll();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("user/user-dataset.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "USER", ignore);
        Assert.assertEquals(expectedData.getTable("USER").getRowCount(), users.size());
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

}