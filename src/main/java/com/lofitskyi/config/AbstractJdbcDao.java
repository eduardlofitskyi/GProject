package com.lofitskyi.config;

import com.lofitskyi.repository.PersistentException;

import java.sql.Connection;

public abstract class AbstractJdbcDao {
    public abstract Connection createConnection() throws PersistentException;
}
