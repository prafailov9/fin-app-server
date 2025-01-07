package com.project.app.daos.instrument;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.exceptions.PrepareStatementFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultInstrumentDao extends AbstractGenericDao<InstrumentDto> implements InstrumentDao {

    protected static final Logger log = LoggerFactory.getLogger(DefaultInstrumentDao.class);

    private static final String TABLE_NAME = "instruments";

    private static final String SELECT_ONE_BY_NAME_QUERY = "select * from instruments where name='?'";

    public DefaultInstrumentDao() {
        super(TABLE_NAME);
    }

    @Override
    public List<InstrumentDto> loadAllByType(final String type) {
        String query = "select * from instruments where instrument_type=?";
        try (PreparedStatement pst = getConnection().prepareStatement(query)) {
            pst.setString(1, type);
            ResultSet results = pst.executeQuery();
            return getAllDatabaseResults(results);
        } catch (SQLException ex) {
            log.error("Could not load instruments by given type: {}", type, ex);
        }
        return null;
    }

    @Override
    public InstrumentDto loadOneByName(final String name) {
        InstrumentDto dto;
        try (PreparedStatement pst = getConnection().prepareStatement(SELECT_ONE_BY_NAME_QUERY)) {
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            dto = getDatabaseResults(rs);
            return dto;
        } catch (SQLException ex) {
            log.error("Could not load instrument by given name: {}", name, ex);
        }
        return null;
    }

    @Override
    protected InstrumentDto getDatabaseResults(ResultSet rs) throws SQLException {
        InstrumentDto inst = new InstrumentDto();
        while (rs.next()) {
            inst = getResults(rs);
        }
        return inst;
    }

    @Override
    protected PreparedStatement prepareUpdate(InstrumentDto entity, Connection connection) throws PrepareStatementFailedException, SQLException {
        String query = "update " + tableName + " set id=?, instrument_name=?, interest_rate=?, "
                + "start_payment_date=?, end_payment_date=?, interest_frequency=?, "
                + "principal_frequency=?, instrument_type=? where id=" + entity.getId();
        log.info("Update query: {}", query);

        return initPreparedStatement(query, connection, pst -> {
            pst.setLong(1, entity.getId());
            pst.setString(2, entity.getInstrumentName());
            pst.setDouble(3, entity.getInterestRate());
            pst.setTimestamp(4, entity.getPaymentStartingDate());
            pst.setTimestamp(5, entity.getPaymentEndingDate());
            pst.setString(6, entity.getInterestFrequency());
            pst.setString(7, entity.getPrincipalFrequency());
            pst.setString(8, entity.getIntrumentType());
        });
    }

    @Override
    protected String buildInsertQueryWithForeignKeys(InstrumentDto entity) {
        return null; // doesn't have FKs
    }

    @Override
    protected List<InstrumentDto> getAllDatabaseResults(ResultSet rs) throws SQLException {
        List<InstrumentDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(getResults(rs));
        }
        return dtoList;
    }

    @Override
    protected void setEntityId(InstrumentDto entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected boolean containsReference(InstrumentDto entity) {
        return false;
    }


    private InstrumentDto getResults(ResultSet rs) throws SQLException {
        InstrumentDto inst = new InstrumentDto();
        inst.setId(rs.getLong("id"));
        inst.setInstrumentName(rs.getString("instrument_name"));
        inst.setInterestRate(rs.getDouble("interest_rate"));
        inst.setPaymentStartingDate(rs.getTimestamp("start_payment_date"));
        inst.setPaymentEndingDate(rs.getTimestamp("end_payment_date"));
        inst.setInterestFrequency(rs.getString("interest_frequency"));
        inst.setPrincipalFrequency(rs.getString("principal_frequency"));
        inst.setInstrumentType(rs.getString("instrument_type"));
        return inst;
    }

}
