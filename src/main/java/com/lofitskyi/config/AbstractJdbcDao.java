package com.lofitskyi.config;

import java.sql.Connection;

public abstract class AbstractJdbcDao {
    public abstract Connection createConnection();
}
