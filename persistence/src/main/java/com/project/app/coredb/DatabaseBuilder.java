package com.project.app.coredb;

import com.project.app.exceptions.DatabaseInitializationException;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for building a database with initial data without knowing the
 * underlying RDBMS.
 *
 * @author p.rafailov
 */
public class DatabaseBuilder {

    private static final Logger LOGGER = Logger.getLogger(DatabaseBuilder.class.getCanonicalName());
    private static final String RECREATE_DB_SCRIPT_FILE = "/recreate-db-script.sql";
    private static final String DB_SCRIPT_FILE = "/fin-db-script.sql";
    private static final String TEST_DATA_FILE = "/test-data.sql";

    private DatabaseBuilder() {

    }

    public static void recreateDatabase(Connection conn) {
        runScript(conn, RECREATE_DB_SCRIPT_FILE);
    }

    public static void buildDatabase() throws SQLException {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            runScript(conn, DB_SCRIPT_FILE);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error while building the database!", ex);
            throw ex;
        }
    }

    public static void insertData() throws SQLException {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            runScript(conn, TEST_DATA_FILE);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while inserting data", ex);
            throw ex;
        }
    }

    public static void dropDatabase() throws SQLException {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            try (Statement stm = conn.createStatement()) {
                String dropAllTablesQuery = "drop table transactions; drop table positions; drop table instruments;";
                stm.executeUpdate(dropAllTablesQuery);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, String.format("Error during drop database: %s", ex.getMessage()), ex);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not drop database: %s", ex.getMessage()), ex);
            throw ex;
        }
    }

    public static void runScript(Connection conn, String scriptFile) {
        try {
            InputStream inputStream = DatabaseBuilder.class.getResourceAsStream(scriptFile);
            if (inputStream == null) {
                LOGGER.log(Level.SEVERE, "Script file not found: " + scriptFile);
                throw new RuntimeException("Script file not found");
            }
            try (Reader reader = new InputStreamReader(inputStream)) {
                ScriptRunner sr = new ScriptRunner(conn);

                // PrintWriter logWriter = new PrintWriter(new FileWriter("/resources//db-build.log", true));
                sr.setLogWriter(null);

                sr.runScript(reader);
                // logWriter.flush();  // Flush the log buffer
            }
        } catch (IOException | RuntimeSqlException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DatabaseInitializationException(ex);
        }
    }

}
