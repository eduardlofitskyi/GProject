package com.lofitskyi.controller.struts2;

import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminActionTest {

    AdminAction action;

    @Mock
    UserService service;

    @Before
    public void setUp() throws Exception {
        action = new AdminAction(service);
    }

    @Test
    public void execute() throws Exception {
        action.execute();

        verify(service, only()).findAll();
    }

}