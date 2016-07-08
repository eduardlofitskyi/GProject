package com.lofitskyi.controller.struts2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserActionTest {
    private static final String SUCCESS_RESPONSE = "success";

    private UserAction action = new UserAction();

    @Test
    public void execute() throws Exception {
        assertEquals(SUCCESS_RESPONSE, action.execute());
    }
}