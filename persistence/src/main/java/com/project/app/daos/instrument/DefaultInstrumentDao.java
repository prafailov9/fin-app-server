package com.project.app.daos.instrument;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.dtos.instrument.InstrumentDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultInstrumentDao extends AbstractGenericDao<InstrumentDto> implements InstrumentDao {

    private final static String SELECT_ONE_BY_NAME_QUERY = "select * from instruments where name='?'";

    public DefaultInstrumentDao() {
        super("instruments");
    }

    @Override
    public List<InstrumentDto> loadAllByType(final String type) {
        List<InstrumentDto> instrumentsByType = null;
        try {
            String query = "select * from instruments where instrument_type=?";
            PreparedStatement pst = getConnection().prepareStatement(query);
            pst.setString(1, type);
            ResultSet results = pst.executeQuery();
            instrumentsByType = getAllDatabaseResults(results);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultInstrumentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return instrumentsByType;
        }
    }

    @Override
    public InstrumentDto loadOneByName(final String name) {
        InstrumentDto dto = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(SELECT_ONE_BY_NAME_QUERY);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            dto = getDatabaseResults(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultInstrumentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return dto;
        }
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
    protected void runUpdateQuery(InstrumentDto entity, Connection conn) throws SQLException {
        String query = "update " + tableName + " set id=?, instrument_name=?, interest_rate=?, "
                + "start_payment_date=?, end_payment_date=?, interest_frequency=?, "
                + "principal_frequency=?, instrument_type=? where id=" + entity.getId();
        LOGGER.log(Level.INFO, "Update query: {0}", query);
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setLong(1, entity.getId());
        pst.setString(2, entity.getInstrumentName());
        pst.setDouble(3, entity.getInterestRate());
        pst.setTimestamp(4, entity.getPaymentStartingDate());
        pst.setTimestamp(5, entity.getPaymentEndingDate());
        pst.setString(6, entity.getInterestFrequency());
        pst.setString(7, entity.getPrincipalFrequency());
        pst.setString(8, entity.getIntrumentType());

        pst.executeUpdate();
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

    @Override
    protected Long getEntityId(InstrumentDto entity) {
        return entity.getId();
    }

    @Override
    protected Long getReferenceId(InstrumentDto entity) {
        return null;
    }

    @Override
    protected String getReferenceTableName(InstrumentDto entity) {
        return null;
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
