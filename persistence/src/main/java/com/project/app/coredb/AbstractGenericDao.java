package com.project.app.coredb;

import com.project.app.exceptions.CannotPersistEntityException;
import com.project.app.exceptions.NoRecordFoundException;
import com.project.app.exceptions.NoSuchEntityException;
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

    @Override
    public T loadById(final Long id) {
        Connection connection = getConnection();
        T entity = null;
        PreparedStatement pst = null;
        ResultSet results = null;
        try {
            String query = String.format(LOAD_ONE_QUERY, tableName);
            pst = connection.prepareStatement(query);

            pst.setLong(1, id);
            results = pst.executeQuery();
            entity = getDatabaseResults(results);
            if (Objects.isNull(entity) || Objects.isNull(getEntityId(entity))) {
                throw new NoRecordFoundException();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not load a record with id=" + id + " from " + tableName + "!", ex);
        } finally {
            closeDatabaseResources(results, pst);
        }
        return entity;
    }

    @Override
    public List<T> loadAll() {
        Connection connection = getConnection();
        PreparedStatement pst = null;
        ResultSet results = null;
        try {
            String query = String.format(LOAD_ALL_QUERY, tableName);
            pst = connection.prepareStatement(query);
            results = pst.executeQuery();

            return getAllDatabaseResults(results);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not load all records from " + tableName + "!", ex);
        } finally {
            closeDatabaseResources(results, pst);
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        Connection connection = getConnection();
        Long entityId = getEntityId(entity);
        PreparedStatement pst = null;
        try {
            if (entityId == null) {
                throw new NoSuchEntityException();
            } else {
                String query = String.format(DELETE_QUERY, tableName);
                pst = connection.prepareStatement(query);
                pst.setLong(1, entityId);
                pst.executeUpdate();
                setEntityId(entity, null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not delete record with id=" + entityId + "from " + tableName + "!", ex);
        } finally {
            closeDatabaseResources(null, pst);
        }
    }

    @Override
    public void update(final T entity) {
        Connection connection = getConnection();
        try {
            if (Objects.isNull(getEntityId(entity))) {
                throw new NoSuchEntityException();
            }
            runUpdateQuery(entity, connection);

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not update a record in " + tableName + "!", ex);
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
        Connection connection = getConnection();
        String query = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String innerQuery = String.format(LOAD_ONE_QUERY, getReferenceTableName(entity));
        try {
            pst = connection.prepareStatement(innerQuery);
            pst.setLong(1, getReferenceId(entity));
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
            closeDatabaseResources(rs, pst);
        }
        return query;
    }

    private void runInsertQuery(T entity, String query) {
        Connection connection = getConnection();
        PreparedStatement insertStatement = null;
        ResultSet keys = null;
        try {
            LOGGER.log(Level.INFO, "Query: {0}", query);
            insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.executeUpdate();
            keys = insertStatement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                setEntityId(entity, id);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not create a record in " + tableName + "!", ex);
            throw new CannotPersistEntityException();
        } finally {
            closeDatabaseResources(keys, insertStatement);
        }
    }


    private Connection getConnection() {
        return DatabaseConnector.getInstance().getConnection();
    }

    /**
     * Method to close the db resources once used. If not closed, these objects might potentially create memory leaks
     * and other unwanted behavior.
     *
     * @param rs  - result set
     * @param stm - statement
     */
    protected void closeDatabaseResources(final ResultSet rs, final Statement stm) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not close ResultSet in " + tableName + "!", ex);
        }
        try {
            if (stm != null)
                stm.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not close Statement in " + tableName + "!", ex);
        }
    }

    protected abstract Long getEntityId(T entity);

    protected abstract void setEntityId(T entity, Long id);

    protected abstract Long getReferenceId(T entity);

    protected abstract String getReferenceTableName(T entity);

    protected abstract T getDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract List<T> getAllDatabaseResults(ResultSet rs) throws SQLException;

    protected abstract boolean containsReference(T entity);

    protected abstract void runUpdateQuery(T entity, Connection conn) throws SQLException;

}
