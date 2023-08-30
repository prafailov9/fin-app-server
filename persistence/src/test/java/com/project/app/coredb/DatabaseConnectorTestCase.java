package com.project.app.coredb;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectorTestCase {

    private static final String PROPERTIES_PATH = "/db-test.properties";
    @Before
    public void setUp() {

    }

    @Test
    public void connectToDBTest() throws SQLException {
        DatabaseConnector.initialize(PROPERTIES_PATH);
        Connection conn = DatabaseConnector.getInstance().getConnection();
        String actualUrl = conn.getMetaData().getURL();
        System.out.println(actualUrl);
    }

}
