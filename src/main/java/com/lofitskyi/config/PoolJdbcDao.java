package com.lofitskyi.config;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PoolJdbcDao extends AbstractJdbcDao{

    private static BasicDataSource ds;

    public Connection createConnection(){

        //TODO change on spring's Environment in further
        if (ds == null) {
            FileInputStream fis;
            Properties property = new Properties();
            ds = new BasicDataSource();

            try {
                fis = new FileInputStream("src/main/resources/db.properties");
                property.load(fis);

                ds.setUrl(property.getProperty("db.url"));
                ds.setDriverClassName(property.getProperty("db.driver"));
                ds.setUsername(property.getProperty("db.username"));
                ds.setPassword(property.getProperty("db.password"));

                ds.setInitialSize(Integer.valueOf(property.getProperty("db.initialSize")));
                ds.setMinIdle(Integer.valueOf(property.getProperty("db.minIdle")));
                ds.setMaxIdle(Integer.valueOf(property.getProperty("db.maxIdle")));
                ds.setTimeBetweenEvictionRunsMillis(Long.valueOf(property.getProperty("db.timeBetweenEvictionRunsMillis")));
                ds.setMinEvictableIdleTimeMillis(Long.valueOf(property.getProperty("db.minEvictableIdleTimeMillis")));
                ds.setTestOnBorrow(Boolean.valueOf(property.getProperty("db.testOnBorrow")));
            } catch (FileNotFoundException e) {
                System.err.println("db.properties doesn't exist");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Wrong db.properties format");
                e.printStackTrace();
            }

        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            System.err.println("Error when try to open connection");
            e.printStackTrace();
        }
        return conn;
    }
}
