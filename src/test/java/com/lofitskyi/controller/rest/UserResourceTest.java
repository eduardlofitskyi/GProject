package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.Role;
import com.lofitskyi.entity.User;
import com.lofitskyi.service.UserService;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    private static final String PREFIX = "user/";

    private static final User VALID_USER = new User(100_100L, "myLogin", "myPass", "em@ali.com", "myName", "mySurname", Date.valueOf(LocalDate.now()), new Role(101_101L, "my_role"));
    private static final Long INVALID_ID = -1L;
    private static final String INVALID_EMAIL = "INVALID";

    private Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    private UserResource resource;

    @Mock
    private UserService service;

    @Before
    public void setUp() throws Exception {
        resource = new UserResource(service);
        dispatcher.getRegistry().addSingletonResource(resource);
    }

    @After
    public void removeResource() {
        dispatcher.getRegistry().removeRegistrations(UserResource.class);
    }

    @Test
    public void findAll() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "all");
        MockHttpResponse response = new MockHttpResponse();

        when(service.findAll()).thenReturn(Arrays.asList(VALID_USER, VALID_USER, VALID_USER));

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        verify(service, only()).findAll();
    }

    @Test
    public void findByLogin() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "username/" + VALID_USER.getLogin());
        MockHttpResponse response = new MockHttpResponse();

        when(service.findByLogin(VALID_USER.getLogin())).thenReturn(VALID_USER);

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertEquals(getExpectedOnFindBy(), response.getContentAsString());

        verify(service, only()).findByLogin(VALID_USER.getLogin());
    }

    @Test
    public void findByEmail() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "email/" + VALID_USER.getEmail());
        MockHttpResponse response = new MockHttpResponse();

        when(service.findByEmail(VALID_USER.getEmail())).thenReturn(VALID_USER);

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertEquals(getExpectedOnFindBy(), response.getContentAsString());

        verify(service, only()).findByEmail(VALID_USER.getEmail());
    }

    @Test
    public void findByEmailInvalidParams() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "email/" + INVALID_EMAIL);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void findById() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "id/" + VALID_USER.getId());
        MockHttpResponse response = new MockHttpResponse();

        when(service.findById(VALID_USER.getId())).thenReturn(VALID_USER);

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertEquals(getExpectedOnFindBy(), response.getContentAsString());

        verify(service, only()).findById(VALID_USER.getId());
    }

    @Test
    public void findByIdInvalidParams() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "id/" + INVALID_ID);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void remove() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "remove/" + VALID_USER.getId());
        MockHttpResponse response = new MockHttpResponse();

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        doNothing().when(service).remove(captor.capture());

        dispatcher.invoke(request, response);


        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        verify(service, only()).remove(any(User.class));
    }

    @Test
    public void removeInvalid() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "remove/" + INVALID_ID);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void create() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "create");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getValidUserJsonRequestBody(true).getBytes());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        doNothing().when(service).create(captor.capture());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        verify(service, only()).create(any(User.class));
    }

    @Test
    public void createInvalid() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "create");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getInvalidUserJsonRequestBody(true).getBytes());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        verify(service, never()).create(any(User.class));
    }

    @Test
    public void update() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "update");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getValidUserJsonRequestBody(false).getBytes());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        doNothing().when(service).update(captor.capture());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        verify(service, only()).update(any(User.class));
    }

    @Test
    public void updateInvalid() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "update");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getInvalidUserJsonRequestBody(false).getBytes());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        verify(service, never()).create(any(User.class));
    }


    private String getExpectedOnFindBy() {
        String expectation = "{" +
                "  \"id\": " + VALID_USER.getId() + "," +
                "  \"login\":\"" + VALID_USER.getLogin() + "\"," +
                "  \"password\":\"" + VALID_USER.getPassword() + "\"," +
                "  \"email\": \"" + VALID_USER.getEmail() + "\"," +
                "  \"firstName\": \"" + VALID_USER.getFirstName() + "\"," +
                "  \"lastName\": \"" + VALID_USER.getLastName() + "\"," +
                "  \"birthday\": \"" + VALID_USER.getBirthday() + "\"," +
                "  \"role\": {" +
                "    \"id\": " + VALID_USER.getRole().getId() + "," +
                "    \"name\": \"" + VALID_USER.getRole().getName() + "\"" +
                "  }" +
                "}";
        expectation = expectation.replace(" ", "");

        return expectation;
    }

    private String getValidUserJsonRequestBody(boolean isNew) {
        StringBuilder json =  new StringBuilder("{\n");

        if (!isNew) json.append("\"id\": 100100,\n");

        json.append("    \"login\": \"user777\",\n" +
                    "    \"password\": \"new_pass\",\n" +
                    "    \"email\": \"new@email.com\",\n" +
                    "    \"firstName\": \"fNew\",\n" +
                    "    \"lastName\": \"lNew\",\n" +
                    "    \"birthday\": \"1999-09-09\",\n" +
                    "    \"role\": {\n" +
                    "      \"name\": \"user\"\n" +
                    "    }\n" +
                    "}");

        return json.toString();
    }

    private String getInvalidUserJsonRequestBody(boolean isNew) {
        StringBuilder json =  new StringBuilder("{\n");

        if (!isNew) json.append("\"id\": 100100,\n");

        json.append("    \"login\": \"!!!\",\n" +
                    "    \"password\": \"new_pass\",\n" +
                    "    \"email\": \"bad-email\",\n" +
                    "    \"firstName\": \"fNew\",\n" +
                    "    \"lastName\": \"lNew\",\n" +
                    "    \"birthday\": \"1999-09-09\",\n" +
                    "    \"role\": {\n" +
                    "      \"name\": \"user\"\n" +
                    "    }\n" +
                    "}");

        return json.toString();
    }
}