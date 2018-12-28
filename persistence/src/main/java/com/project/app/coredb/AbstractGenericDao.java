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

    protected final static Logger LOGGER = Logger.getLogger(AbstractGenericDao.class.getCanonicalName());

    private final static String INSERT_QUERY = "insert into %s values (%s)";
    private final static String INSERT_WITH_FK_QUERY = "insert into %s values (%s, (select id from %s where id = %s));";
    private final static String DELETE_QUERY = "delete from %s where id=?";
    private final static String LOAD_ONE_QUERY = "select * from %s where id=?";
    private final static String LOAD_ALL_QUERY = "select * from %s";

    private Connection connection;
    protected String tableName;

    public AbstractGenericDao(String tableName) {
        this.tableName = tableName;
        DatabaseConnection DBC = DatabaseConnection.getInstance();
        this.connection = DBC.getConnection();

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
        T entity = null;
        try {
            String query = String.format(LOAD_ONE_QUERY, tableName);
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setLong(1, id);
            ResultSet results = pst.executeQuery();
            entity = getDatabaseResults(results);
            if (Objects.isNull(entity) || Objects.isNull(getEntityId(entity))) {
                throw new NoRecordFoundException();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not load a record with id=" + id + " from " + tableName + "!", ex);
        } finally {
            return entity;
        }
    }

    @Override
    public List<T> loadAll() {
        try {
            String query = String.format(LOAD_ALL_QUERY, tableName);
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet results = pst.executeQuery();

            List<T> dtoList = getAllDatabaseResults(results);
            return dtoList;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not load all records from " + tableName + "!", ex);
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        Long entityId = getEntityId(entity);
        try {
            if (Objects.isNull(entityId)) {
                throw new NoSuchEntityException();
            } else {
                String query = String.format(DELETE_QUERY, tableName);
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setLong(1, entityId);
                pst.executeUpdate();
                setEntityId(entity, null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not delete record with id=" + entityId + "from " + tableName + "!", ex);
        }
    }

    @Override
    public void update(final T entity) {
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
        if (Objects.nonNull(getEntityId(entity))) {
            throw new CannotPersistEntityException();
        }
    }

    private void saveWithReference(T entity) {
        checkIfPersisted(entity);
        String query = formInsertWithFKQuery(entity);
        runInsertQuery(entity, query, connection);
    }

    private void saveNoReference(T entity) {
        checkIfPersisted(entity);
        String query = String.format(INSERT_QUERY, tableName, entity.toString());
        runInsertQuery(entity, query, connection);

    }

    private String formInsertWithFKQuery(T entity) {
        String query = null;

        String innerQuery = String.format(LOAD_ONE_QUERY, getReferenceTableName(entity));
        try {
            PreparedStatement pst = connection.prepareStatement(innerQuery);
            pst.setLong(1, getReferenceId(entity));
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new CannotPersistEntityException();
            } else {
                Long fk = rs.getLong(1);
                query = String.format(INSERT_WITH_FK_QUERY, tableName, entity.toString(), getReferenceTableName(entity), fk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractGenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return query;
    }

    private void runInsertQuery(T entity, String query, Connection conn) {
        try {
            LOGGER.log(Level.INFO, "Query: {0}", query);
            PreparedStatement insertStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.executeUpdate();
            ResultSet keys = insertStatement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                setEntityId(entity, id);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not create a record in " + tableName + "!", ex);
            throw new CannotPersistEntityException();
        }
    }

    protected Connection getConnection() {
        return connection;
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
