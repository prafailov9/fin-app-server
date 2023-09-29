package com.project.app.daos.transaction;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.daos.position.DefaultPositionDao;
import com.project.app.dtos.position.PositionDto;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.exceptions.SaveForEntityFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultTransactionDao extends AbstractGenericDao<TransactionDto> implements TransactionDao {

    protected static final Logger log = LoggerFactory.getLogger(DefaultTransactionDao.class);

    private final static String LOAD_ALL_BY_FK_QUERY = "select * from %s where %s=?";

    private final static String INSERT_WITH_FK_SQL = "INSERT INTO transactions VALUES (%s)";

    public DefaultTransactionDao() {
        super("transactions");
    }

    @Override
    public List<TransactionDto> loadAllByReference(Long fk) {
        List<TransactionDto> transactions = null;
        try {
            String query = String.format(LOAD_ALL_BY_FK_QUERY, tableName, "fk_position");
            PreparedStatement pst = getConnection().prepareStatement(query);
            pst.setLong(1, fk);
            ResultSet results = pst.executeQuery();
            transactions = getAllDatabaseResults(results);
        } catch (SQLException ex) {
            log.error("Failed to load transactions for positionId: {}", fk, ex);
        } finally {
            return transactions;

        }
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
        PositionDto pos = new DefaultPositionDao().loadById(rs.getLong("fk_position"));
        tx.setPosition(pos);
        return tx;
    }

    @Override
    protected void setEntityId(TransactionDto entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected boolean containsReference(TransactionDto entity) {
        if (Objects.isNull(entity.getPosition()) || Objects.isNull(entity.getPosition().getId())) {
            throw new SaveForEntityFailedException();
        } else {
            return true;
        }
    }

    @Override
    protected void runUpdateQuery(TransactionDto entity) throws SQLException {
        Connection conn = getConnection();
        String sql = "update " + tableName + " set id=?, amount=?, sign=?, transaction_date=?, fk_position=? where id=" + entity.getId() + ";";

        log.info("Update query: {}", sql);

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setLong(1, entity.getId());
        pst.setDouble(2, entity.getAmount());
        pst.setInt(3, entity.getSign());
        pst.setTimestamp(4, entity.getTransactionDate());
        pst.setLong(5, entity.getPosition().getId());
        pst.executeUpdate();

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
