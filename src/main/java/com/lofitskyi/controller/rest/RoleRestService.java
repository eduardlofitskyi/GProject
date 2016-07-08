package com.lofitskyi.controller.rest;

import com.lofitskyi.entity.Role;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/role")
public class RoleRestService {

    @Autowired
    RoleService service;

    @GET
    @Path("/name/{role_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Role getByName(@PathParam("role_name") String name) throws PersistentException {
        return service.findByName(name);
    }

    @GET
    @Path("/remove/{id}")
    public Response remove(@Min(value = 1, message = "Id cannot be a negative number")
                               @PathParam("id") Long id) throws PersistentException {
        Role role = new Role();
        role.setId(id);

        service.remove(role);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Role role) throws PersistentException {
        service.create(role);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid Role role) throws PersistentException {
        service.update(role);

        return Response.status(Response.Status.OK).build();
    }
}
