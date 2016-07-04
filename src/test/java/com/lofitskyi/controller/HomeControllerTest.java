package com.lofitskyi.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class HomeControllerTest {

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("./");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void shouldReturnHomePageOnIndex() throws Exception {
        mockMvc.perform(get("/index")).andExpect((view().name("home")));
    }

    @Test
    public void shouldReturnHomePageOnIncorrectInput() throws Exception {
        mockMvc.perform(get("/abra-cadabra")).andExpect((view().name("home")));
    }

    @Test
    public void shouldReturnLoginPageOnSignIn() throws Exception {
        mockMvc.perform(get("/login")).andExpect((view().name("login")));
    }

    @Test
    public void shouldReturnSignUpFormPageOnSignUp() throws Exception {
        mockMvc.perform(get("/signupform")).andExpect((view().name("signup")));
    }
}