package com.project.app.coredb;

import com.project.app.exceptions.CannotPersistEntityException;
import com.project.app.exceptions.NoRecordFoundException;
import com.project.app.exceptions.NoSuchEntityException;
import com.project.app.exceptions.PrepareStatementFailedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Plamen
 * @param <T>
 */
public abstract class AbstractGenericDao<T> implements GenericDao<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractGenericDao.class.getCanonicalName());

    private static final String INSERT_QUERY = "insert into %s values (%s)";
    private static final String INSERT_WITH_FK_QUERY = "insert into %s values (%s, (select id from %s where id = %s));";
    private static final String DELETE_QUERY = "delete from %s where id=?";
    private static final String LOAD_ONE_QUERY = "select * from %s where id=?";
    private static final String LOAD_ALL_QUERY = "select * from %s";

    protected String tableName;

    protected AbstractGenericDao(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void save(final T entity) {
        if (containsReference(entity)) {
            saveWithReference(entity);
        } else {
            saveNoReference(entity);
        }
    }

    /** IMPLEMENT THIS FOR EVERY METHOD
     *
     * @Override
     * public T loadById(final Long id) {
     *     T entity = null;
     *     Connection connection = null;
     *     PreparedStatement preparedStatement = null;
     *     ResultSet resultSet = null;
     *
     *     try {
     *         connection = acquireConnection();
     *         preparedStatement = prepareStatement(connection, id);
     *         resultSet = executeQuery(preparedStatement);
     *         entity = processResultSet(resultSet, id);
     *     } catch (SQLException | NoRecordFoundException ex) {
     *         LOGGER.log(Level.SEVERE, "Exception while loading record with id=" + id + " from " + tableName, ex);
     *     } finally {
     *         closeResources(resultSet, preparedStatement, connection);
     *     }
     *
     *     return entity;
     * }
     *
     * private Connection acquireConnection() throws SQLException {
     *     return DatabaseConnector.getInstance().getConnection();
     * }
     *
     * private PreparedStatement prepareStatement(Connection connection, Long id) throws SQLException {
     *     String query = "SELECT * FROM " + tableName + " WHERE id=?";
     *     PreparedStatement preparedStatement = connection.prepareStatement(query);
     *     preparedStatement.setLong(1, id);
     *     return preparedStatement;
     * }
     *
     * private ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
     *     return preparedStatement.executeQuery();
     * }
     *
     * private T processResultSet(ResultSet resultSet, Long id) throws SQLException, NoRecordFoundException {
     *     if (resultSet.next()) {
     *         return getDatabaseResults(resultSet);
     *     } else {
     *         throw new NoRecordFoundException();
     *     }
     * }
     *
     */

    @Override
    public T loadById(final Long id) {
        T entity = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = String.format(LOAD_ONE_QUERY, tableName);
            pst = initPreparedStatement(sql, conn, preparedStatement -> {
                assert preparedStatement != null;
                preparedStatement.setLong(1, id);
            });

            rs = pst.executeQuery();
            entity = getDatabaseResults(rs);
            if (entity == null || getEntityId(entity) == null) {
                throw new NoRecordFoundException();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not load a record with id=%s from %s", id, tableName), ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return entity;
    }

    @Override
    public List<T> loadAll() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = String.format(LOAD_ALL_QUERY, tableName);
            pst = initPreparedStatement(sql, conn, null);
            rs = pst.executeQuery();

            return getAllDatabaseResults(rs);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not load all records from %s", tableName), ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        Connection conn = null;
        Long entityId = getEntityId(entity);
        PreparedStatement pst = null;
        try {
            if (entityId == null) {
                throw new NoSuchEntityException();
            } else {

                conn = getConnection();
                String sql = String.format(DELETE_QUERY, tableName);
                pst = initPreparedStatement(sql, conn, preparedStatement -> {
                    assert preparedStatement != null;
                    preparedStatement.setLong(1, entityId);
                });
                pst.executeUpdate();
                setEntityId(entity, null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not delete record with id=%s from %s", entityId, tableName), ex);
        } finally {
            closeResources(null, pst, conn);
        }
    }

    @Override
    public void update(final T entity) {
        try {
            if (Objects.isNull(getEntityId(entity))) {
                throw new NoSuchEntityException();
            }
            runUpdateQuery(entity);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not update a record in %s", tableName), ex);
        }

    }

    private void checkIfPersisted(T entity) {
        if (getEntityId(entity) != null) {
            throw new CannotPersistEntityException();
        }
    }

    private void saveWithReference(T entity) {
        checkIfPersisted(entity);
        String query = formInsertWithFKQuery(entity);
        runInsertQuery(entity, query);
    }

    private void saveNoReference(T entity) {
        checkIfPersisted(entity);
        String query = String.format(INSERT_QUERY, tableName, entity.toString());
        runInsertQuery(entity, query);

    }

    private String formInsertWithFKQuery(T entity) {
        Connection conn = null;
        String query = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String innerQuery = String.format(LOAD_ONE_QUERY, getReferenceTableName(entity));
        try {
            conn = getConnection();
            pst = initPreparedStatement(innerQuery, conn, preparedStatement -> {
                assert preparedStatement != null;
                preparedStatement.setLong(1, getReferenceId(entity));
            });
            rs = pst.executeQuery();
            if (!rs.next()) {
                throw new CannotPersistEntityException();
            } else {
                Long fk = rs.getLong(1);
                query = String.format(INSERT_WITH_FK_QUERY, tableName, entity.toString(), getReferenceTableName(entity), fk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractGenericDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return query;
    }

    private void runInsertQuery(T entity, String query) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            LOGGER.log(Level.INFO, "Query: {0}", query);
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            keys = pst.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                setEntityId(entity, id);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Could not create a record in %s", tableName), ex);
            throw new CannotPersistEntityException();
        } finally {
            closeResources(keys, pst, conn);
        }
    }

    protected Connection getConnection() {
        return DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Will init prepared statement based on a specific DB operation
     * @param sql
     * @param connection
     * @param binder
     * @return
     * @throws SQLException
     */
    protected PreparedStatement initPreparedStatement(final String sql, Connection connection, PreparedStatementBinder binder) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(preparedStatement);
            }
            return preparedStatement;
        } catch (SQLException ex) {
            String error = String.format("Error during preparing sql statement %s", sql);
            LOGGER.log(Level.SEVERE, error, ex);
            throw new PrepareStatementFailedException(error, ex.getCause());
        }
    }

    /**
     * Method to close the db resources once used. If not closed, these objects might potentially create memory leaks
     * and other unwanted behavior.
     *
     * @param resultSet  - result set
     * @param preparedStatement - precompiled sql statement
     * @param connection - db connection
     */
    protected void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to close resources", e);
        }
    }

    protected abstract Long getEntityId(T entity);

    protected abstract void setEntityId(T entity, Long id);

    protected abstract Long getReferenceId(T entity);

    protected abstract String getReferenceTableName(T entity);

    protected abstract T getDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract List<T> getAllDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract boolean containsReference(T entity);

    protected abstract void runUpdateQuery(T entity) throws SQLException;

}
