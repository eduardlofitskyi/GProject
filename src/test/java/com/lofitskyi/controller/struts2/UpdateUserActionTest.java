package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateUserActionTest {

    private static final String DATE = "1999-01-01";
    private static final String USER_ROLE = "user";
    private static final String SUCCESS_RESPONSE = "success";

    UpdateUserAction action;

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
        MockitoAnnotations.initMocks(this);

        action = new UpdateUserAction(service, roleService);
    }

    @Test
    public void shouldUpdateUserOnValidAction() throws Exception {

        action.setId(user.getId());
        action.setLogin(user.getLogin());
        action.setPassword(user.getPassword());
        action.setEmail(user.getEmail());
        action.setFirstName(user.getFirstName());
        action.setLastName(user.getLastName());
        action.setBirthday(DATE);
        action.setRole(USER_ROLE);

        when(roleService.findByName("user")).thenReturn(new Role(1L, "user"));

        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn(USER_ROLE);
        when(roleService.findByName(USER_ROLE)).thenReturn(role);
        doCallRealMethod().when(user).setRole(role);

        assertEquals(SUCCESS_RESPONSE, action.execute());

        verify(roleService, only()).findByName(USER_ROLE);
        verify(service, only()).update(any(User.class));
    }

}