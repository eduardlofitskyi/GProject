package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.Role;
import com.lofitskyi.service.RoleService;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleResourceTest {

    public static final String PREFIX = "role/";
    private static final Role ROLE = new Role(100_100L, "mock_role");

    private Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    private RoleResource resource;

    @Mock
    private RoleService service;

    @Before
    public void setUp() throws Exception {
        resource = new RoleResource(service);
        dispatcher.getRegistry().addSingletonResource(resource);
    }

    @After
    public void removeResource() {
        dispatcher.getRegistry().removeRegistrations(RoleResource.class);
    }

    @Test
    public void findByName() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "name/" + ROLE.getName());
        MockHttpResponse response = new MockHttpResponse();

        when(service.findByName(ROLE.getName())).thenReturn(ROLE);

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertEquals("{\"id\":" + ROLE.getId() + ",\"name\":\"" + ROLE.getName() + "\"}", response.getContentAsString());
        verify(service, only()).findByName(ROLE.getName());
    }

    @Test
    public void remove() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "remove/100100");
        MockHttpResponse response = new MockHttpResponse();

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        doNothing().when(service).remove(captor.capture());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        verify(service, only()).remove(any(Role.class));
    }

    @Test
    public void removeInvalidParam() throws Exception {
        MockHttpRequest request = MockHttpRequest.get(PREFIX + "remove/-100100");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        verify(service, never()).remove(any(Role.class));
    }

    @Test
    public void create() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "create");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getRoleJsonRequestParam(true).getBytes());

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        doNothing().when(service).create(captor.capture());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        verify(service, only()).create(any(Role.class));
    }

    @Test
    public void update() throws Exception {
        MockHttpRequest request = MockHttpRequest.post(PREFIX + "update");
        MockHttpResponse response = new MockHttpResponse();

        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(getRoleJsonRequestParam(false).getBytes());

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        doNothing().when(service).update(captor.capture());

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        verify(service, only()).update(any(Role.class));
    }

    private String getRoleJsonRequestParam(boolean isNew) {
        StringBuilder json =  new StringBuilder("{\n");

        if (!isNew) json.append("\"id\": 100100,\n");

        json.append("    \"name\": \"new_role\"\n" +
                    "}");

        return json.toString();
    }
}