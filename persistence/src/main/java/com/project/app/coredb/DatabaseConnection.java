package com.project.app.coredb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/*
    Class for creating a database connection without knowing the underlying RDBMS.
    Only one unique instance of this class can exist at any given time.
 */
public class DatabaseConnection {

    private final static Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getCanonicalName());

    private DataSource dataSource;
    private Properties properties;
    private Connection connection;

    private DatabaseConnection(String propertiesFile) {
        try {
            this.properties = loadProperties(propertiesFile);
            this.dataSource = loadDataSource();
            this.connection = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class Holder {

        static DatabaseConnection INSTANCE;

        static final void setInstance(final String properties) {
            INSTANCE = new DatabaseConnection(properties);
        }
    }

    public static void initialize(final String properties) {
        Holder.setInstance(properties);

    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Properties getProperties() {
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    private Properties loadProperties(String propertiesFile) {
        try {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream(propertiesFile));
            properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        } 
        return properties;
    }

    private DataSource loadDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("datasource.driver"));
        ds.setUrl(properties.getProperty("datasource.url"));
        ds.setUsername(properties.getProperty("datasource.username"));
        ds.setPassword(properties.getProperty("datasource.password"));
        return ds;
    }

}
