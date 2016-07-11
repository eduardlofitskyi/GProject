package com.lofitskyi.service.springdata;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.springdata.RoleRepository;
import com.lofitskyi.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    private static final String DUMMY_ROLE_STR = "ROLE_";
    private static final Role DUMMY_ROLE = new Role(1L, DUMMY_ROLE_STR);

    private RoleService service;

    @Mock
    RoleRepository repository;

    @Before
    public void setUp() throws Exception {
        service = new RoleServiceImpl(repository);
    }

    @Test
    public void create() throws Exception {
        service.create(DUMMY_ROLE);

        verify(repository, only()).saveAndFlush(DUMMY_ROLE);
    }

    @Test
    public void update() throws Exception {
        service.update(DUMMY_ROLE);

        verify(repository, only()).saveAndFlush(DUMMY_ROLE);
    }

    @Test
    public void remove() throws Exception {
        service.remove(DUMMY_ROLE);

        verify(repository, only()).delete(DUMMY_ROLE);
    }

    @Test
    public void findByName() throws Exception {
        when(repository.findByName(DUMMY_ROLE_STR)).thenReturn(DUMMY_ROLE);

        service.findByName(DUMMY_ROLE_STR);

        verify(repository, only()).findByName(DUMMY_ROLE_STR);
    }

}