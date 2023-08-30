package com.project.app.coredb;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectiorTestCase {

    private static final String PROPERTIES_PATH = "/db-test.properties";
    private DatabaseConnector DBC;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        DBC = null;
    }

    @Test
    public void connectToDBTest() throws SQLException {
        DatabaseConnector.initialize(PROPERTIES_PATH);
        DatabaseConnector DBC = DatabaseConnector.getInstance();
        Connection conn = DBC.getConnection();
        String actualUrl = conn.getMetaData().getURL();
        System.out.println(actualUrl);
    }

}
