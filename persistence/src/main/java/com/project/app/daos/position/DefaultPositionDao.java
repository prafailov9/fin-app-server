package com.project.app.daos.position;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.exceptions.CannotPersistEntityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DefaultPositionDao extends AbstractGenericDao<PositionDto> implements PositionDao {

    private final static String LOAD_ALL_BY_FK_QUERY = "select * from %s where %s=?";

    private final InstrumentDao instrumentDao;

    public DefaultPositionDao() {
        super("positions");
        instrumentDao = new DefaultInstrumentDao();
    }

    @Override
    public List<PositionDto> loadAllByTypeOfReference(InstrumentDto instrument) {

        List<InstrumentDto> ins = instrumentDao.loadAllByType(instrument.getIntrumentType());
        List<Long> fks = ins.stream().map(in -> in.getId()).collect(Collectors.toList());
        List<PositionDto> positionsByReferenceType = new ArrayList<>();
        fks.forEach((fk) -> {
            positionsByReferenceType.addAll(loadAllByReference(fk));
        });
        return positionsByReferenceType;
    }

    @Override
    public List<PositionDto> loadAllByReference(Long fk) {
        List<PositionDto> pos = new ArrayList<>();
        try {
            String query = String.format(LOAD_ALL_BY_FK_QUERY, tableName, "fk_instrument");
            PreparedStatement pst = getConnection().prepareStatement(query);
            pst.setLong(1, fk);
            ResultSet results = pst.executeQuery();
            pos = getAllDatabaseResults(results);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultPositionDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return pos;
        }
    }

    @Override
    protected PositionDto getDatabaseResults(ResultSet rs) throws SQLException {
        PositionDto pos = new PositionDto();
        while (rs.next()) {
            pos = getSingleResultRow(rs);

        }
        return pos;
    }

    private PositionDto getSingleResultRow(ResultSet rs) throws SQLException {
        PositionDto pos = new PositionDto();
        pos.setId(rs.getLong("id"));
        pos.setDealStartingDate(rs.getTimestamp("deal_start_date"));
        pos.setPayer(rs.getString("payer_name"));
        pos.setReceiver(rs.getString("receiver_name"));
        pos.setPrincipal(rs.getDouble("principal"));
        pos.setPositionVolume(rs.getDouble("position_volume"));
        Long fk = rs.getLong("fk_instrument");
        InstrumentDto inst = new DefaultInstrumentDao().loadById(fk);
        pos.setInstrument(inst);

        return pos;
    }

    @Override
    protected void setEntityId(PositionDto entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected boolean containsReference(PositionDto entity) {
        if (Objects.isNull(entity.getInstrument()) || Objects.isNull(entity.getInstrument().getId())) {
            throw new CannotPersistEntityException();
        }
        return true;
    }

    @Override
    protected Long getEntityId(PositionDto entity) {
        return entity.getId();
    }

    @Override
    protected void runUpdateQuery(PositionDto entity) throws SQLException {
        Connection conn = getConnection();
        String query = "update " + tableName + " set id=?, deal_start_date=?, payer_name=?, "
                + "receiver_name=?, principal=?, position_volume=?, fk_instrument=? where id=" + entity.getId() + ";";
        LOGGER.log(Level.INFO, "Update query: {0}", query);
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setLong(1, entity.getId());
        pst.setTimestamp(2, entity.getDealStartingDate());
        pst.setString(3, entity.getPayer());
        pst.setString(4, entity.getReceiver());
        pst.setDouble(5, entity.getPrincipal());
        pst.setDouble(6, entity.getPositionVolume());
        pst.setLong(7, entity.getInstrument().getId());
        int affectedRows = pst.executeUpdate();
        LOGGER.log(Level.INFO, "Affected rows after position Update: {0}", affectedRows);
    }

    @Override
    protected List<PositionDto> getAllDatabaseResults(ResultSet rs) throws SQLException {
        List<PositionDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            PositionDto pos = getSingleResultRow(rs);
            dtoList.add(pos);
        }
        return dtoList;
    }

    @Override
    protected Long getReferenceId(PositionDto entity) {
        return entity.getInstrument().getId();
    }

    @Override
    protected String getReferenceTableName(PositionDto entity) {
        return "instruments";
    }

}
