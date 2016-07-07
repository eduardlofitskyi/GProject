package com.lofitskyi.controller.struts2;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginRedirectActionTest {

    private static final String SUCCESS_RESPONSE = "success";

    private LoginRedirectAction action = new LoginRedirectAction();

    @Test
    public void execute() throws Exception {
        assertEquals(SUCCESS_RESPONSE, action.execute());
    }

}