package com.lofitskyi.controller.struts2;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.RoleService;
import com.lofitskyi.service.UserService;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SignUpAction.class)
public class SignUpActionTest {

    private static final String DATE = "1999-01-01";
    private static final String USER_ROLE = "user";
    private static final String SUCCESS_RESPONSE = "success";
    private static final String INPUT_RESPONSE = "input";

    SignUpAction actionSpy;

    @Mock
    UserService service;

    @Mock
    RoleService roleService;

    @Mock
    ReCaptcha reCaptcha;

    @Mock
    ReCaptchaResponse reCaptchaResponse;

    @Mock
    User user;

    @Mock
    Role role;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        actionSpy = PowerMockito.spy(new SignUpAction(service, roleService, reCaptcha));

        PowerMockito.doReturn("").when(actionSpy, "getRemoteAddress");
    }

    @Test
    public void shouldSignUpWhenValidAction() throws Exception {
        actionSpy.setBirthday(DATE);
        actionSpy.setUser(user);

        when(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).thenReturn(reCaptchaResponse);
        when(reCaptchaResponse.isValid()).thenReturn(true);
        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn(USER_ROLE);
        when(roleService.findByName(USER_ROLE)).thenReturn(role);
        doCallRealMethod().when(user).setRole(role);

        assertEquals(SUCCESS_RESPONSE,  actionSpy.execute());

        verify(roleService, only()).findByName(USER_ROLE);
        verify(service, only()).create(user);
    }

    @Test
    public void shouldNotSignUpWhenWrongCaptcha() throws Exception {
        actionSpy.setBirthday(DATE);
        actionSpy.setUser(user);

        when(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).thenReturn(reCaptchaResponse);
        when(reCaptchaResponse.isValid()).thenReturn(false);

        assertEquals(INPUT_RESPONSE,  actionSpy.execute());

        verify(roleService, never()).findByName(anyString());
        verify(service, never()).create(any(User.class));
    }
}