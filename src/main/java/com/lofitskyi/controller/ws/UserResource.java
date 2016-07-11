package com.lofitskyi.controller.ws;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.PersistentException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService(serviceName = "UserResource")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserResource {

    @WebMethod
    User[] findAll() throws PersistentException;

    @WebMethod
    User findById(Long id) throws PersistentException;

    @WebMethod
    User findByLogin(String login) throws PersistentException;

    @WebMethod
    User findByEmail(String email) throws PersistentException;

    @WebMethod
    void create(User user) throws SQLException, PersistentException;

    @WebMethod
    void update(User user) throws PersistentException;

    @WebMethod
    void remove(User user) throws PersistentException;
}
