package com.lofitskyi.utils;

import com.lofitskyi.config.AbstractDataSource;
import org.dbunit.IDatabaseTester;

import java.sql.Connection;

public class DataSourceTestAdapter extends AbstractDataSource {

    private final IDatabaseTester tester;

    public DataSourceTestAdapter(IDatabaseTester tester) {
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
