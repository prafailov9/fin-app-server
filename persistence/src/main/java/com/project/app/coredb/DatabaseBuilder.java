package com.project.app.coredb;

import com.project.app.exceptions.DatabaseInitializationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;

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
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not drop database", ex);
            throw ex;
        }
    }

    public static void runScript(Connection conn, String scriptFile) {
        try (InputStream inputStream = InputStream.class.getResourceAsStream(scriptFile);
            Reader reader = new InputStreamReader(inputStream)) {
            ScriptRunner sr = new ScriptRunner(conn);
            sr.setLogWriter(null);
            sr.runScript(reader);
        } catch (IOException | RuntimeSqlException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DatabaseInitializationException(ex);
        }
    }
}
