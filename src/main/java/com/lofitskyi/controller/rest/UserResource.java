package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.hibernate.validator.constraints.Email;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Component
@Path("/user")
public class UserResource {

    @Autowired
    UserService service;

    public UserResource() {
    }

    public UserResource(UserService service){
        this.service = service;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() throws PersistentException {
        return service.findAll();
    }

    @GET
    @Path("/username/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getByLogin(@PathParam("login") String login) throws PersistentException {
        return service.findByLogin(login);
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getByEmail(@Email(message = "Not valid email")
                               @PathParam("email") String email) throws PersistentException {
        return service.findByEmail(email);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getById(@Min(value = 1, message = "Id cannot be a negative number")
                            @PathParam("id") Long id) throws PersistentException {
        return service.findById(id);
    }

    @GET
    @Path("/remove/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(@Min(value = 1, message = "Id cannot be a negative number")
                               @PathParam("id") Long id) throws PersistentException {

        User user = new User();
        user.setId(id);

        service.remove(user);

        return Response.status(Response.Status.OK).entity(id).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid User user) throws PersistentException, SQLException {

        service.create(user);

        return Response.status(Response.Status.CREATED).entity(user.getId()).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid User user) throws PersistentException {

        service.update(user);

        return Response.status(Response.Status.OK).entity(user.getId()).build();
    }

}
