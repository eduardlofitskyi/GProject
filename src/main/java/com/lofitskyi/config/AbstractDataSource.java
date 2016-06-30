package com.lofitskyi.config;

import com.lofitskyi.service.PersistentException;

import java.sql.Connection;

public abstract class AbstractDataSource {
    public abstract Connection createConnection() throws PersistentException;
}
