package com.lofitskyi.repository.springdata;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.lofitskyi.config.DatabaseConfig;
import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldFindUserByLogin() throws PersistentException {
        final User user = repository.findByLogin("user1");
        Assert.assertEquals("email1@mail.com", user.getEmail());
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldThrowExceptionWhenTryFindUserWithNotPresentedLogin() throws PersistentException {
        Assert.assertNull(repository.findByLogin("NINJA"));
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldFindUserByEmail() throws PersistentException {
        final User user = repository.findByEmail("email1@mail.com");
        Assert.assertEquals("user1", user.getLogin());
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldThrowExceptionWhenTryFindUserWithNotPresentedEmail() throws PersistentException {
        Assert.assertNull(repository.findByEmail("not_email"));
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldFindUserById() throws PersistentException {
        final User user = repository.findById(1L);
        Assert.assertEquals("user1", user.getLogin());
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldThrowExceptionWhenTryFindUserWithNotPresentedId() throws PersistentException {
        Assert.assertNull(repository.findById(-100_000L));
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldFindAllUser() throws PersistentException {
        Assert.assertEquals(3, repository.findAll().size());
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    @ExpectedDatabase(value = "/user/user-dataset-save.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldCreateNewUser() throws PersistentException {
        Role adminRole = roleRepository.findByName("admin");
        User user = new User("new_user", "new_pass", "new_email@mail.com", "new_f_name", "new_surname", new Date(95, 0, 1), adminRole);
        repository.saveAndFlush(user);
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    @ExpectedDatabase(value = "/user/user-dataset-update.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldUpdateUser() throws PersistentException {
        User updatedUser = repository.findByLogin("user1");
        updatedUser.setLogin("upd_user");
        repository.saveAndFlush(updatedUser);
    }

    @Test
    @DatabaseSetup("/user/user-dataset.xml")
    public void shouldRemoveUser() throws PersistentException {
        User user = repository.findByLogin("user1");
        repository.delete(user);

        assertNull(repository.findByLogin("user1"));
    }
}