package com.project.app.controller;

import com.project.app.Main;
import com.project.app.coredb.DatabaseBuilder;
import com.project.app.coredb.DatabaseConnector;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static com.project.app.Main.BASE_URI;
import static com.project.app.Main.startServer;
import static com.project.app.coredb.DatabaseBuilder.dropDatabase;
import static jakarta.ws.rs.client.ClientBuilder.newClient;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 *
 * @author p.rafailov
 */
public abstract class AbstractResourceTest {

    private final static String PROPERTIES_PATH = "/db-test.properties";
    private static HttpServer server;
    private static WebTarget target;
    private final ThreadLocalRandom generator = ThreadLocalRandom.current();
    protected final static Logger LOGGER = Logger.getLogger(AbstractResourceTest.class.getCanonicalName());

    @BeforeClass
    public static void setUpClass() {
        try {
            DatabaseConnector.initialize(PROPERTIES_PATH);
            DatabaseBuilder.buildDatabase();
            DatabaseBuilder.insertData();
        } catch (SQLException ex) {
            LOGGER.log(SEVERE, "Error occured while building test database!", ex);
        }
        LOGGER.log(INFO, "IN SET UP ABSTRACT CLASS!");
        // start the server
        server = startServer();
        // create the client
        Client c = newClient();
        target = c.target(BASE_URI);

    }

    @AfterClass
    public static void tearDownClass() {
        try {
            LOGGER.log(INFO, "IN TEAR DOWN ABSTRACT CLASS!");
            dropDatabase();
            server.shutdownNow();
        } catch (SQLException ex) {
            LOGGER.log(SEVERE, "Error occurred while dropping test database!", ex);
        }
    }

    protected WebTarget getWebTarget() {
        return target;
    }

    protected ThreadLocalRandom getGenerator() {
        return generator;
    }

    protected Long getRandomId() {
        List<Long> ids = getAllIds();
        return ids.get(generator.nextInt(ids.size()));
    }

    protected abstract List<Long> getAllIds();

}
