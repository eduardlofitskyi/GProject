package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangeActionTest {

    private static final String WRONG_TYPE = "abra-cadabra";
    private static final String EDIT_TYPE = "edit";
    private static final String ADD_TYPE = "add";

    private static final String ERROR_RESPONSE = "error";
    private static final String EDIT_RESPONSE = "update";
    private static final String ADD_RESPONSE = "add";
    private static final Long USER_ID = 100_000L;

    private static final User user = new User(USER_ID, "", "", "", "", "", Date.valueOf(LocalDate.now()), new Role(""));

    ChangeAction action;

    @Mock
    UserService service;

    @Before
    public void setUp() throws Exception {
        action = new ChangeAction(service);
    }

    @Test
    public void shouldReturnErrorWhenWrongType() throws Exception {
        action.setType(WRONG_TYPE);

        assertEquals(ERROR_RESPONSE, action.execute());
    }

    @Test
    public void shouldReturnEditResponseWhenEditType() throws Exception {
        action.setType(ADD_TYPE);

        assertEquals(ADD_RESPONSE, action.execute());
    }

    @Test
    public void shouldReturnAddResponseWhenAddType() throws Exception {
        when(service.findById(USER_ID)).thenReturn(user);

        action.setType(EDIT_TYPE);
        action.setId(USER_ID);

        assertEquals(EDIT_RESPONSE, action.execute());
        verify(service, only()).findById(USER_ID);
    }
}