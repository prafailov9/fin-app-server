package com.project.app.coredb;

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

    private final static Logger LOGGER = Logger.getLogger(DatabaseBuilder.class.getCanonicalName());
    private final static String RECREATE_DB_SCRIPT_FILE = "/recreate-db-script.sql";
    private final static String DB_SCRIPT_FILE = "/fin-db-script.sql";
    private final static String TEST_DATA_FILE = "/test-data.sql";

    public static void recreateDatabase(Connection conn) {
        String message = "Error occured while recreating database!";
        runScript(conn, RECREATE_DB_SCRIPT_FILE, message);
    }

    public static void buildDatabase(Connection conn) throws SQLException {
        String message = "Error occured while building database!";
        runScript(conn, DB_SCRIPT_FILE, message);
    }

    public static void insertData(Connection conn) throws SQLException {
        String message = "Error occured while inserting test data!";
        runScript(conn, TEST_DATA_FILE, message);
    }

    public static void dropDatabase(Connection conn) throws SQLException {
        Statement stm = conn.createStatement();
        String dropAllTablesQuery = "drop table transactions; drop table positions; drop table instruments;";
        stm.executeUpdate(dropAllTablesQuery);
    }

    public final static void runScript(Connection conn, String scriptFile, String message) {
        try {
            InputStream inputStream = InputStream.class.getResourceAsStream(scriptFile);
            Reader reader = new InputStreamReader(inputStream);
            ScriptRunner sr = new ScriptRunner(conn);
            sr.setLogWriter(null); // Script runner will not log the file contents.
            sr.runScript(reader);
        } catch (RuntimeSqlException ex) {
            LOGGER.log(Level.INFO, message, ex);
        }

    }
}
