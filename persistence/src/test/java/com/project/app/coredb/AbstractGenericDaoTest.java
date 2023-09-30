package com.project.app.coredb;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractGenericDaoTest<T> {

    protected final static Logger LOGGER = Logger.getLogger(AbstractGenericDaoTest.class.getCanonicalName());
    private static final String PROPERTIES_PATH = "/db-test.properties";

    @BeforeClass
    public static void beforeClass() {
        LOGGER.log(Level.INFO, "In abstract Dao test class. Setting up test connection...");
        try {
            DatabaseConnector.initialize(PROPERTIES_PATH);
            LOGGER.log(Level.INFO, "Building test database...");
            DatabaseBuilder.buildDatabase();
            LOGGER.log(Level.INFO, "Inserting test data...");
            DatabaseBuilder.insertData();
            LOGGER.log(Level.INFO, "Test database configured!");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception in abstract dao test class: {0}", ex.getMessage());
        }
    }

    @AfterClass
    public static void afterClass() {
        LOGGER.log(Level.INFO, "In abstract test class.");
        try {
            DatabaseBuilder.dropDatabase();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception in abstract test class: {0}", ex.getMessage());
        }
    }

    public Long getRandomId() {
        List<Long> ids = getAllIds();
        return ids.get(new Random().nextInt(ids.size()));
    }

    protected List<Long> getAllIds() {
        List<T> records = getRecords();
        return records.stream().map(this::getDtoId).collect(Collectors.toList());
    }

    protected abstract List<T> getRecords();

    protected abstract Long getDtoId(T dto);

}
