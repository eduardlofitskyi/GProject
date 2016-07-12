package com.lofitskyi.controller.rest;

import com.lofitskyi.repository.PersistentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistentExceptionHandler implements ExceptionMapper<PersistentException>{

    @Override
    public Response toResponse(PersistentException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
