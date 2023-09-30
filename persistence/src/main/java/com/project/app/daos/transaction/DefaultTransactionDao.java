package com.project.app.daos.transaction;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.daos.position.PositionDao;
import com.project.app.dtos.position.PositionDto;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.exceptions.PrepareStatementFailedException;
import com.project.app.exceptions.SaveForEntityFailedException;
import com.project.app.factory.DaoInstanceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultTransactionDao extends AbstractGenericDao<TransactionDto> implements TransactionDao {

    protected static final Logger log = LoggerFactory.getLogger(DefaultTransactionDao.class);

    private static final String TABLE_NAME = "transactions";

    private final static String LOAD_ALL_BY_FK_QUERY = "select * from %s where %s=?";
    private final static String INSERT_WITH_FK_SQL = "INSERT INTO transactions VALUES (%s)";

    private final PositionDao positionDao;

    public DefaultTransactionDao() {
        super(TABLE_NAME);
        this.positionDao = DaoInstanceHolder.get(PositionDao.class);
    }

    @Override
    public List<TransactionDto> loadAllByReference(Long fk) {
        List<TransactionDto> transactions = null;
        PreparedStatement pst = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            String query = String.format(LOAD_ALL_BY_FK_QUERY, tableName, "fk_position");
            conn = getConnection();
            pst = conn.prepareStatement(query);
            pst.setLong(1, fk);
            rs = pst.executeQuery();
            transactions = getAllDatabaseResults(rs);
        } catch (SQLException ex) {
            log.error("Failed to load transactions for positionId: {}", fk, ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return transactions;
    }

    @Override
    protected TransactionDto getDatabaseResults(ResultSet rs) throws SQLException {
        TransactionDto tx = new TransactionDto();
        while (rs.next()) {
            tx = getResults(rs);
        }
        return tx;
    }

    private TransactionDto getResults(ResultSet rs) throws SQLException {
        TransactionDto tx = new TransactionDto();
        tx.setId(rs.getLong("id"));
        tx.setAmount(rs.getDouble("amount"));
        tx.setSign(rs.getInt("sign"));
        tx.setTransactionDate(rs.getTimestamp("transaction_date"));
        PositionDto pos = positionDao.loadById(rs.getLong("fk_position"));
        tx.setPosition(pos);
        return tx;
    }

    @Override
    protected void setEntityId(TransactionDto entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected boolean containsReference(TransactionDto entity) {
        if (entity.getPosition() == null || entity.getPosition().getId() == null) {
            throw new SaveForEntityFailedException();
        }
        return true;
    }

    @Override
    protected PreparedStatement prepareUpdate(TransactionDto entity, Connection connection) throws PrepareStatementFailedException, SQLException {
        String sql = "update " + tableName + " set id=?, amount=?, sign=?, transaction_date=?, fk_position=? where id=" + entity.getId() + ";";
        log.info("Update query: {}", sql);

        return initPreparedStatement(sql, connection, pst -> {
            pst.setLong(1, entity.getId());
            pst.setDouble(2, entity.getAmount());
            pst.setInt(3, entity.getSign());
            pst.setTimestamp(4, entity.getTransactionDate());
            pst.setLong(5, entity.getPosition().getId());
        });
    }

    @Override
    protected String buildInsertQueryWithForeignKeys(TransactionDto entity) {
        if (entity.getPosition() == null) {
            throw new RuntimeException(String.format("Cannot save position %s without an instrument_id", entity));
        }
        String insertQuery = String.format(INSERT_WITH_FK_SQL, entity.getDataAsString());
        log.info("Built insert query: {}", insertQuery);

        return insertQuery;
    }

    @Override
    protected List<TransactionDto> getAllDatabaseResults(ResultSet rs) throws SQLException {
        List<TransactionDto> txList = new ArrayList<>();
        while (rs.next()) {
            TransactionDto tx = getResults(rs);
            txList.add(tx);
        }
        return txList;
    }

}
