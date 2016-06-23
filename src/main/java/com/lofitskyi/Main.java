package com.lofitskyi;

import com.lofitskyi.config.AbstractJdbcDao;
import com.lofitskyi.config.PoolJdbcDao;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        AbstractJdbcDao dataSource = new PoolJdbcDao();
        Connection conn = dataSource.createConnection();
        System.out.println("I'm here");
    }
}
