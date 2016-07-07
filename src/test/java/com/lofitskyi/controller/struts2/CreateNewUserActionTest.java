package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewUserActionTest{

    private static final String DATE = "1999-01-01";
    private static final String USER_ROLE = "user";
    private static final String SUCCESS_RESPONSE = "success";

    CreateNewUserAction action;

    @Mock
    UserService service;

    @Mock
    RoleService roleService;

    @Mock
    User user;

    @Mock
    Role role;

    @Before
    public void setUp() throws Exception {
        action = new CreateNewUserAction(service, roleService);
    }

    @Test
    public void shouldCreateForValidParams() throws Exception {
        action.setUser(user);
        action.setBirthday(DATE);

        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn(USER_ROLE);
        when(roleService.findByName(USER_ROLE)).thenReturn(role);
        doCallRealMethod().when(user).setRole(role);

        assertEquals(SUCCESS_RESPONSE,  action.execute());

        verify(roleService, only()).findByName(USER_ROLE);
        verify(service, only()).create(user);
    }

}