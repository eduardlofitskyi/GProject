package com.lofitskyi.utils;

import com.lofitskyi.config.AbstractJdbcDao;
import org.dbunit.IDatabaseTester;

import java.sql.Connection;

public class JdbcDaoTestAdapter extends AbstractJdbcDao{

    private final IDatabaseTester tester;

    public JdbcDaoTestAdapter(IDatabaseTester tester) {
        this.tester = tester;
    }

    @Override
    public Connection createConnection() {
        try {
            return tester.getConnection().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
