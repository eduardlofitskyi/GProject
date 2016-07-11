package com.lofitskyi.service.springdata;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.springdata.UserRepository;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String DUMMY_USER_LOGIN = "LOGIN_";
    private static final String DUMMY_USER_EMAIL = "EMAIL_";
    private static final Long DUMMY_USER_ID = -100_000L;

    private UserService service;

    @Mock
    UserRepository repository;

    @Mock
    User dummy;

    @Before
    public void setUp() throws Exception {
        service = new UserServiceImpl(repository);
    }

    @Test
    public void create() throws Exception {
        service.create(dummy);

        verify(repository, only()).saveAndFlush(dummy);
    }

    @Test
    public void update() throws Exception {
        service.update(dummy);

        verify(repository, only()).saveAndFlush(dummy);
    }

    @Test
    public void remove() throws Exception {
        service.remove(dummy);

        verify(repository, only()).delete(dummy);
    }

    @Test
    public void findAll() throws Exception {
        service.findAll();

        verify(repository, only()).findAll();
    }

    @Test
    public void findByLogin() throws Exception {
        when(repository.findByLogin(DUMMY_USER_LOGIN)).thenReturn(dummy);

        service.findByLogin(DUMMY_USER_LOGIN);

        verify(repository, only()).findByLogin(DUMMY_USER_LOGIN);
    }

    @Test
    public void findByEmail() throws Exception {
        when(repository.findByEmail(DUMMY_USER_EMAIL)).thenReturn(dummy);

        service.findByEmail(DUMMY_USER_EMAIL);

        verify(repository, only()).findByEmail(DUMMY_USER_EMAIL);
    }

    @Test
    public void findById() throws Exception {
        when(repository.findById(DUMMY_USER_ID)).thenReturn(dummy);

        service.findById(DUMMY_USER_ID);

        verify(repository, only()).findById(DUMMY_USER_ID);
    }

}