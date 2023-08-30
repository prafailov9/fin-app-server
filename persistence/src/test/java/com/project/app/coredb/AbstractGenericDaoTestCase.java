package com.project.app.coredb;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractGenericDaoTestCase<T> {

    protected final static Logger LOGGER = Logger.getLogger(AbstractGenericDaoTestCase.class.getCanonicalName());
    private static final String PROPERTIES_PATH = "/db-test.properties";
    private static DatabaseConnector databaseConnection;

    @BeforeClass
    public static void beforeClass() {
        LOGGER.log(Level.INFO, "In abstract Dao test class. Setting up test connection...");
        try {
            DatabaseConnector.initialize(PROPERTIES_PATH);
            databaseConnection = DatabaseConnector.getInstance();
            LOGGER.log(Level.INFO, "Building test database...");
            DatabaseBuilder.buildDatabase(databaseConnection.getConnection());
            LOGGER.log(Level.INFO, "Inserting test data...");
            DatabaseBuilder.insertData(databaseConnection.getConnection());
            LOGGER.log(Level.INFO, "Test database configured!");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception in abstract dao test class: {0}", ex.getMessage());
        }
    }

    @AfterClass
    public static void afterClass() {
        LOGGER.log(Level.INFO, "In abstract test class.");
        try {
            DatabaseBuilder.dropDatabase(databaseConnection.getConnection());
            databaseConnection.closeConnection();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception in abstract test class: {0}", ex.getMessage());
        }
    }

    public Long getRandomId() {
        List<Long> ids = getAllIds();
        Long randomId = ids.get(new Random().nextInt(ids.size()));
        return randomId;
    }

    protected List<Long> getAllIds() {
        List<T> records = getRecords();
        List<Long> ids = records.stream().map(t -> getDtoId(t)).collect(Collectors.toList());
        return ids;
    }

    protected abstract List<T> getRecords();

    protected abstract Long getDtoId(T dto);

}
