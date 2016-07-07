package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoveUserActionTest {

    private static final String SUCCESS_RESPONSE = "success";

    RemoveUserAction action;

    @Mock
    UserService service;

    @Before
    public void setUp() throws Exception {
        action = new RemoveUserAction(service);
    }

    @Test
    public void shouldDeleteOnAction() throws Exception {
        assertEquals(SUCCESS_RESPONSE, action.execute());

        verify(service, only()).remove(any(User.class));
    }
}