package com.project.app.coredb;

import com.project.app.exceptions.CannotLoadPropertiesException;
import com.project.app.exceptions.ConnectorNotInitException;
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
public class DatabaseConnector {

    private final static Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getCanonicalName());

    private DataSource dataSource;
    private Properties properties;

    private DatabaseConnector(String propertiesFile) {
        this.properties = loadProperties(propertiesFile);
        this.dataSource = loadDataSource();
    }

    private static class InstanceHolder {

        static DatabaseConnector INSTANCE;

        static final void setInstance(final String properties) {
            INSTANCE = new DatabaseConnector(properties);
        }
    }

    public static void initialize(final String properties) {
        InstanceHolder.setInstance(properties);
    }

    public static DatabaseConnector getInstance() {
        if (InstanceHolder.INSTANCE == null) {
            throw new ConnectorNotInitException("Database Connector not initialized! initialize() must be called before retrieval");
        }
        return InstanceHolder.INSTANCE;
    }

    public Connection getConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return connection;
        } catch (SQLException e) {
            LOGGER.severe("Could not retrieve connection!");
        }
        return null;
    }

    private Properties loadProperties(String propertiesFile) {
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(propertiesFile))) {
            properties = new Properties();
            properties.load(reader);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CannotLoadPropertiesException("Could not load properties! File might not exist, wrong path given, or data is invalid!");
        }
        return properties;
    }

    private DataSource loadDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("datasource.driver"));
        ds.setUrl(properties.getProperty("datasource.url"));
        ds.setUsername(properties.getProperty("datasource.username"));
        ds.setPassword(properties.getProperty("datasource.password"));

        // Configure connection pool properties
        ds.setInitialSize(5); // initial pool size
        ds.setMaxActive(50);   // max active connections
        ds.setMinIdle(5);     // min idle connections
        ds.setMaxIdle(20);    // max idle connections

        return ds;
    }


}
