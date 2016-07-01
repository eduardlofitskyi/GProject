package com.lofitskyi.repository.hibernate;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.RoleDao;
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

import static org.h2.engine.Constants.UTF8;

public class HibernateRoleDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test_hibernate;DB_CLOSE_DELAY=-1";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static SessionFactory sf;

    private RoleDao dao;
    private IDatabaseTester tester;

    @BeforeClass
    public static void startUp() throws Exception {
        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Role.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test_hibernate;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", UTF8, false);

        sf = configuration.buildSessionFactory();
    }

    @Before
    public void setUp() throws Exception {
        dao = new HibernateRoleDao(sf);

        tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("role/role-dataset.xml"));
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    public void shouldFindRoleByName() throws Exception {
        Role role = dao.findByName("admin");

        Assert.assertEquals("admin", role.getName());
    }

    @Test(expected = PersistentException.class)
    public void shouldThrowExceptionWhenTryFoundNotPresentedRole() throws PersistentException {
            dao.findByName("NINJA");
    }

    @Test(expected = PersistentException.class)
    public void shouldRemoveRowFromTable() throws PersistentException {
        Role role = dao.findByName("admin");

        dao.remove(role);

        dao.findByName(role.getName());
    }

    @Test
    public void shouldPersistAndFlushOnCreate() throws Exception {
        Role role = new Role("new_role");

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

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

}