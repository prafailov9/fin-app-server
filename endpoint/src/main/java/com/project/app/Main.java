package com.project.app;

import com.project.app.coredb.DatabaseConnector;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/rest/";

    // Database configuration properties
    private static final String DB_PROPERTIES_PATH = "/db.properties";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.project.app package
        final ResourceConfig rc = new ResourceConfig().packages("com.project.app");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        DatabaseConnector.initialize(DB_PROPERTIES_PATH);
//        DatabaseBuilder.recreateDatabase(DatabaseConnection.getConnection());
        final HttpServer server = startServer();
        System.out.printf("Jersey app started with WADL available at "
                + "%s application.wadl\nHit enter to stop it...%n", BASE_URI);
        server.shutdownNow();
    }

}
