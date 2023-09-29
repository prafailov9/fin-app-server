package com.project.app.coredb;

import com.project.app.dtos.Entity;
import com.project.app.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * @param <T> - Dtos should implement the Entity interface
 * @author Plamen
 */
public abstract class AbstractGenericDao<T extends Entity> implements GenericDao<T> {

    protected static final Logger log = LoggerFactory.getLogger(AbstractGenericDao.class);

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
        requireNotExists(entity);
        if (containsReference(entity)) {
            return saveWithReference(entity);
        } else {
            return plainSave(entity);
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
            pst = initPreparedStatement(query, conn,
                    preparedStatement -> preparedStatement.setLong(1, id));

            rs = pst.executeQuery();
            entity = getDatabaseResults(rs);
            if (entity == null || entity.getId() == null) {
                throw new NoRecordFoundException();
            }
        } catch (SQLException ex) {
            log.error("Could not load a record with id={} from {}", id, tableName, ex);
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
            log.error("Could not load all records from {}", tableName, ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        Connection conn = null;
        Long entityId = entity.getId();
        PreparedStatement pst = null;
        try {
            if (entityId == null) {
                throw new NoSuchEntityException();
            }
            conn = getConnection();
            String query = String.format(DELETE_QUERY, tableName);
            pst = initPreparedStatement(query, conn,
                    preparedStatement -> preparedStatement.setLong(1, entityId));
            pst.executeUpdate();
            setEntityId(entity, null);

        } catch (SQLException ex) {
            log.error("Could not delete record with id={} from {}", entityId, tableName, ex);
        } finally {
            closeResources(null, pst, conn);
        }
    }

    @Override
    public void update(final T entity) {
        try {
            if (entity.getId() == null) {
                throw new NoSuchEntityException();
            }
            runUpdateQuery(entity);
        } catch (SQLException ex) {
            log.error("Could not update a record in {}", tableName, ex);
        }
    }

    private T saveWithReference(T entity) {
        String query = buildInsertQueryWithForeignKeys(entity);
        return runSave(entity, query);
    }

    private T plainSave(T entity) {
        String query = String.format(INSERT_QUERY, tableName, entity.toString());
        return runSave(entity, query);
    }

    private void requireNotExists(T entity) {
        Long id = entity.getId();
        if (id != null) {
            throw new EntityAlreadyExistsException(String.format("Entity from %s already exists whit id = %s", tableName, id));
        }
    }

    private T runSave(T entity, String query) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            log.info("Query: {}", query);
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            keys = pst.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                setEntityId(entity, id);
            }
        } catch (SQLException ex) {
            log.error("Could not create a record in {}", tableName, ex);
            throw new SaveForEntityFailedException();
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
     * @param binder     - binding params
     * @return preparedStatement
     * @throws PrepareStatementFailedException -
     */
    protected PreparedStatement initPreparedStatement(final String sql, Connection connection, PreparedStatementBinder binder) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // will bind different params based on the current operation
            if (binder != null) {
                binder.bind(preparedStatement);
            }
            return preparedStatement;
        } catch (SQLException ex) {
            String error = String.format("Error during preparing sql statement %s", sql);
            log.error(error, ex);
            throw new PrepareStatementFailedException(error, ex.getCause());
        }
    }

    /**
     * Method to close the db resources once used. If not closed, these objects might create memory leaks
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
        } catch (SQLException ex) {
            log.error("Failed to close resources", ex);
            throw new FailedToCloseDBResourcesException(ex);
        }
    }

    protected abstract void setEntityId(T entity, Long id);

    protected abstract T getDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract List<T> getAllDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract boolean containsReference(T entity);

    protected abstract void runUpdateQuery(T entity) throws SQLException;

    protected abstract String buildInsertQueryWithForeignKeys(T entity);

}
