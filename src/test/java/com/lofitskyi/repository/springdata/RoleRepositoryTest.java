package com.lofitskyi.repository.springdata;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.lofitskyi.config.DatabaseConfig;
import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.repository.springdata.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@WebAppConfiguration
public class RoleRepositoryTest {


    @Autowired
    private RoleRepository repository;

    @Test
    @DatabaseSetup("/role/role-dataset.xml")
    public void shouldFindRoleByName() throws PersistentException {
        final Role role = repository.findByName("admin");
        Assert.assertEquals("admin", role.getName());
    }

    @Test
    @DatabaseSetup("/role/role-dataset.xml")
    public void shouldThrowExceptionWhenTryFindNotPresentedRole() throws PersistentException {
        Assert.assertNull(repository.findByName("NINJA"));
    }

    @Test
    @DatabaseSetup("/role/role-dataset.xml")
    @ExpectedDatabase(value = "/role/role-dataset-save.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldCreateNewRole() throws PersistentException {
        Role newRole = new Role("new_role");
        repository.saveAndFlush(newRole);
    }

    @Test
    @DatabaseSetup("/role/role-dataset.xml")
    @ExpectedDatabase(value = "/role/role-dataset-update.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldUpdateRole() throws PersistentException {
        Role updatedRole = repository.findByName("admin");
        updatedRole.setName("super_admin");
        repository.saveAndFlush(updatedRole);
    }

    @Test
    @DatabaseSetup("/role/role-dataset.xml")
    public void shouldRemoveRole() throws PersistentException {
        Role role = repository.findByName("admin");
        repository.delete(role);

        Assert.assertNull(repository.findByName("admin"));
    }
}