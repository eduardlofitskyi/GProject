package com.lofitskyi.controller;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.sql.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final User VALID_USER = new User(1l, "login1", "password1", "email@mail1.com", "firstName1", "lastName1", new Date(95, 1, 1), new Role(1l, "admin"));
    private static final User INVALID_USER = new User(2l, "log", "pas", "email2", "firstName2", "lastName2", new Date(95, 2, 2), new Role(1l, "user"));

    MockMvc mockMvc;

    @Mock
    UserService userServiceMock;
    @Mock
    ReCaptcha reCaptcha;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("./");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userServiceMock, reCaptcha))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void shouldDetermineCorrectWayByResolver() throws Exception {
        mockMvc.perform(get("/change")
                .param("type", "add"))
                .andExpect(view().name("add"));

        mockMvc.perform(get("/change")
                .param("type", "edit")
                .param("id", "1"))
                .andExpect(view().name("update"));

        mockMvc.perform(get("/change")
                .param("type", "any_wrong"))
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void shouldCreateUserWithValidParams() throws Exception {

        mockMvc.perform(post("/add")
                .param("login", VALID_USER.getLogin())
                .param("password", VALID_USER.getPassword())
                .param("email", VALID_USER.getEmail())
                .param("firstName", VALID_USER.getFirstName())
                .param("lastName", VALID_USER.getLastName())
                .param("birthday", VALID_USER.getBirthday().toString())
                .param("role", VALID_USER.getRole().getName())
        )
                .andExpect(redirectedUrl("/admin"));

        verify(userServiceMock, times(1)).create(any());
    }

    @Test
    public void shouldNotCreateUserWithInvalidParams() throws Exception {

        mockMvc.perform(post("/add")
                .param("login", INVALID_USER.getLogin())
                .param("password", INVALID_USER.getPassword())
                .param("email", INVALID_USER.getEmail())
                .param("firstName", INVALID_USER.getFirstName())
                .param("lastName", INVALID_USER.getLastName())
                .param("birthday", INVALID_USER.getBirthday().toString())
                .param("role", INVALID_USER.getRole().getName())
        )
                .andExpect(view().name("add"));

        verify(userServiceMock, times(0)).create(any());
    }

    @Test
    public void shouldUpdateUserWithValidParams() throws Exception {

        mockMvc.perform(post("/upd")
                .param("login", VALID_USER.getLogin())
                .param("password", VALID_USER.getPassword())
                .param("email", VALID_USER.getEmail())
                .param("firstName", VALID_USER.getFirstName())
                .param("lastName", VALID_USER.getLastName())
                .param("birthday", VALID_USER.getBirthday().toString())
                .param("role", VALID_USER.getRole().getName())
        )
                .andExpect(redirectedUrl("/admin"));

        verify(userServiceMock, times(1)).update(any());
    }

    @Test
    public void shouldNotUpdateUserWithInvalidParams() throws Exception {

        mockMvc.perform(post("/upd")
                .param("login", INVALID_USER.getLogin())
                .param("password", INVALID_USER.getPassword())
                .param("email", INVALID_USER.getEmail())
                .param("firstName", INVALID_USER.getFirstName())
                .param("lastName", INVALID_USER.getLastName())
                .param("birthday", INVALID_USER.getBirthday().toString())
                .param("role", INVALID_USER.getRole().getName())
        )
                .andExpect(view().name("update"));

        verify(userServiceMock, times(0)).update(any());
    }



    @Test
    public void shouldSignUpUserWithValidParams() throws Exception {

        ReCaptchaResponse reCaptchaResponseMock = mock(ReCaptchaResponse.class);

        when(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).thenReturn(reCaptchaResponseMock);
        when(reCaptchaResponseMock.isValid()).thenReturn(true);

        mockMvc.perform(post("/signup")
                .param("login", VALID_USER.getLogin())
                .param("password", VALID_USER.getPassword())
                .param("email", VALID_USER.getEmail())
                .param("firstName", VALID_USER.getFirstName())
                .param("lastName", VALID_USER.getLastName())
                .param("birthday", VALID_USER.getBirthday().toString())
                .param("role", VALID_USER.getRole().getName())
        )
                .andExpect(redirectedUrl("/index"));


        verify(userServiceMock, times(1)).create(any());
    }

    @Test
    public void shouldNotSignUpUserWithWithoutCaptcha() throws Exception {

        ReCaptchaResponse reCaptchaResponseMock = mock(ReCaptchaResponse.class);

        when(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).thenReturn(reCaptchaResponseMock);
        when(reCaptchaResponseMock.isValid()).thenReturn(false);

        mockMvc.perform(post("/signup")
                .param("login", VALID_USER.getLogin())
                .param("password", VALID_USER.getPassword())
                .param("email", VALID_USER.getEmail())
                .param("firstName", VALID_USER.getFirstName())
                .param("lastName", VALID_USER.getLastName())
                .param("birthday", VALID_USER.getBirthday().toString())
                .param("role", VALID_USER.getRole().getName())
        )
                .andExpect(view().name("signup"));


        verify(userServiceMock, times(0)).create(any());
    }

    @Test
    public void shouldSignUpUserWithInvalidParams() throws Exception {

        mockMvc.perform(post("/signup")
                .param("login", INVALID_USER.getLogin())
                .param("password", INVALID_USER.getPassword())
                .param("email", INVALID_USER.getEmail())
                .param("firstName", INVALID_USER.getFirstName())
                .param("lastName", INVALID_USER.getLastName())
                .param("birthday", INVALID_USER.getBirthday().toString())
                .param("role", INVALID_USER.getRole().getName())
        )
                .andExpect(view().name("signup"));

        verify(userServiceMock, times(0)).create(any());
    }


    @Test
    public void shouldDeleteUserById() throws Exception {
        mockMvc.perform(get("/del").param("id", "111"))
                .andExpect(redirectedUrl("/admin"));

        verify(userServiceMock, times(1)).remove(any());
    }
}