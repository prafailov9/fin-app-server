package com.project.app.coredb;

import com.project.app.exceptions.CannotSaveEntityException;
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
 * @param <T>
 * @author Plamen
 */
public abstract class AbstractGenericDao<T> implements GenericDao<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractGenericDao.class.getCanonicalName());

    private static final String INSERT_QUERY = "insert into %s values (%s)";
    private static final String DELETE_QUERY = "delete from %s where id=?";
    private static final String LOAD_ONE_QUERY = "select * from %s where id=?";
    private static final String LOAD_ALL_QUERY = "select * from %s";

    protected String tableName;

    protected AbstractGenericDao(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public T save(final T entity) {
        if (containsReference(entity)) {
            return saveWithReference(entity);
        } else {
            return saveNoReference(entity);
        }
    }

    @Override
    public T loadById(final Long id) {
        T entity = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = String.format(LOAD_ONE_QUERY, tableName);
            pst = initPreparedStatement(query, conn, preparedStatement -> {
                if (preparedStatement != null) {
                    preparedStatement.setLong(1, id);
                }
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
            String query = String.format(LOAD_ALL_QUERY, tableName);
            pst = initPreparedStatement(query, conn, null);
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
                String query = String.format(DELETE_QUERY, tableName);
                pst = initPreparedStatement(query, conn, preparedStatement -> {
                    if (preparedStatement != null) {
                        preparedStatement.setLong(1, entityId);
                    }
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

    private T saveWithReference(T entity) {
        checkIfPersisted(entity);
        String query = buildInsertQueryWithForeignKeys(entity);
        return runInsertQuery(entity, query);
    }

    private T saveNoReference(T entity) {
        checkIfPersisted(entity);
        String query = String.format(INSERT_QUERY, tableName, entity.toString());
        return runInsertQuery(entity, query);
    }

    private void checkIfPersisted(T entity) {
        if (getEntityId(entity) != null) {
            throw new CannotSaveEntityException();
        }
    }

    private T runInsertQuery(T entity, String query) {
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
            throw new CannotSaveEntityException();
        } finally {
            closeResources(keys, pst, conn);
        }
        return entity;
    }

    protected Connection getConnection() {
        return DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Will init prepared statement based on a specific DB operation
     *
     * @param sql        - query
     * @param connection - db conn
     * @param binder     - binding params based on the db operation
     * @return preparedStatement
     * @throws PrepareStatementFailedException -
     */
    protected PreparedStatement initPreparedStatement(final String sql, Connection connection, PreparedStatementBinder binder) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
     * @param resultSet         - result set
     * @param preparedStatement - precompiled sql statement
     * @param connection        - db connection
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

    protected abstract T getDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract List<T> getAllDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract boolean containsReference(T entity);

    protected abstract void runUpdateQuery(T entity) throws SQLException;

    protected abstract String buildInsertQueryWithForeignKeys(T entity);

}
