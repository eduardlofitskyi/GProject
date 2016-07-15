package com.lofitskyi.controller;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class VerifyControllerTest {

    private static final User USER_A = new User(1l, "login1", "password1", "email@mail1.com", "firstName1", "lastName1", new Date(95, 1, 1), new Role(1l, "admin"));
    private static final User USER_B = new User(2l, "login2", "password2", "email@mail2.com", "firstName2", "lastName2", new Date(95, 2, 2), new Role(1l, "user"));

    MockMvc mockMvc;

    @Mock
    UserService userServiceMock;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("./");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new VerifyController(userServiceMock))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void shouldReturnUserPageForUsualUser() throws Exception {
        mockMvc.perform(get("/user")).andExpect((view().name("user")));
    }

    @Test
    public void shouldReturnAdminPageForAdminUser() throws Exception {
        when(userServiceMock.findAll()).thenReturn(getUserList());

        mockMvc.perform(get("/admin"))
                .andExpect((view().name("admin")))
                .andExpect(model().attribute("userList", hasSize(2)));

        verify(userServiceMock).findAll();
    }

    private List<User> getUserList() {
        return Arrays.asList(USER_A, USER_B);
    }


}