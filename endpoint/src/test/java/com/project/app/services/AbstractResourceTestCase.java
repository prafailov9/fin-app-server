package com.project.app.services;

import com.project.app.Main;
import com.project.app.coredb.DatabaseBuilder;
import com.project.app.coredb.DatabaseConnector;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author p.rafailov
 */
public abstract class AbstractResourceTestCase {

    private final static String PROPERTIES_PATH = "/db-test.properties";
    private static HttpServer server;
    private static WebTarget target;
    private static DatabaseConnector databaseConnection;
    private final ThreadLocalRandom generator = ThreadLocalRandom.current();
    protected final static Logger LOGGER = Logger.getLogger(AbstractResourceTestCase.class.getCanonicalName());

    @BeforeClass
    public static void setUpClass() {
        try {
            DatabaseConnector.initialize(PROPERTIES_PATH);
            DatabaseBuilder.buildDatabase();
            DatabaseBuilder.insertData();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error occured while building test database!", ex);
        }
        LOGGER.log(Level.INFO, "IN SET UP ABSTRACT CLASS!");
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);

    }

    @AfterClass
    public static void tearDownClass() {
        try {
            LOGGER.log(Level.INFO, "IN TEAR DOWN ABSTRACT CLASS!");
            DatabaseBuilder.dropDatabase();
            server.shutdownNow();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error occured while dropping test database!", ex);
        }
    }

    protected HttpServer getHttpServer() {
        return server;
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
