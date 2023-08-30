package com.project.app.businesslogic;

import com.project.app.coredb.DatabaseBuilder;
import com.project.app.coredb.DatabaseConnector;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author p.rafailov
 */
public abstract class AbstractEntityBLTestCase {

    private final static String PROPERTIES_PATH = "/db-test.properties";
    private final ThreadLocalRandom generator = ThreadLocalRandom.current();
    private static DatabaseConnector databaseConnection;
    protected final static Logger LOGGER = Logger.getLogger(AbstractEntityBLTestCase.class.getCanonicalName());

    @BeforeClass
    public static void beforeClass() {
        try {
            LOGGER.log(Level.INFO, "Creating database connection...");
            DatabaseConnector.initialize(PROPERTIES_PATH);
            databaseConnection = DatabaseConnector.getInstance();
            LOGGER.log(Level.INFO, "Building test database...");
            DatabaseBuilder.buildDatabase(databaseConnection.getConnection());
            DatabaseBuilder.insertData(databaseConnection.getConnection());
            LOGGER.log(Level.INFO, "Test database build successfull!");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error occured while building test database!");
        }

    }

    @AfterClass
    public static void tearDownClass() {
        try {
            LOGGER.log(Level.INFO, "Dropping database...");
            DatabaseBuilder.dropDatabase(databaseConnection.getConnection());
            databaseConnection.closeConnection();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error occured while dropping test database!");
        }
    }

    public Long getRandomId() {
        List<Long> ids = getAllIds();
        return ids.get(generator.nextInt(ids.size()));
    }

    protected abstract List<Long> getAllIds();

}
