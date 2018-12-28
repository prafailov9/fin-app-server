package com.project.app.coredb;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectionTestCase {

    private static final String PROPERTIES_PATH = "/db-test.properties";
    private DatabaseConnection DBC;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        DBC = null;
    }

    @Test
    public void connectToDBTest() throws SQLException {
        DatabaseConnection.initialize(PROPERTIES_PATH);
        DatabaseConnection DBC = DatabaseConnection.getInstance();
        String expectedUrl = DBC.getProperties().getProperty("datasource.url");
        Connection conn = DBC.getConnection();
        String actualUrl = conn.getMetaData().getURL();
        System.out.println(actualUrl);
        assertEquals(expectedUrl, actualUrl);
    }

}
