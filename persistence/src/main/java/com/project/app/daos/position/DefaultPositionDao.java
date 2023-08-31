package com.project.app.daos.position;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.exceptions.CannotSaveEntityException;
import com.project.app.factory.DaoInstanceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultPositionDao extends AbstractGenericDao<PositionDto> implements PositionDao {

    private final static String LOAD_ALL_BY_FK_QUERY = "select * from %s where %s=?";

    private final static String INSERT_WITH_FK_SQL = "INSERT INTO positions VALUES (%s)";

    public DefaultPositionDao() {
        super("positions");
    }

    @Override
    public List<PositionDto> loadAllByInstrumentType(InstrumentDto instrument) {
        InstrumentDao instrumentDao = (InstrumentDao) DaoInstanceHolder.get("instrument");
        List<InstrumentDto> ins = instrumentDao.loadAllByType(instrument.getIntrumentType());

        List<Long> fks = ins.stream().map(InstrumentDto::getId).toList();

        List<PositionDto> positions = new ArrayList<>();
        fks.forEach((fk) -> positions.addAll(loadAllByReference(fk)));
        return positions;
    }

    @Override
    public List<PositionDto> loadAllByReference(Long fk) {
        List<PositionDto> pos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = String.format(LOAD_ALL_BY_FK_QUERY, tableName, "fk_instrument");
            pst = initPreparedStatement(query, conn, preparedStatement -> {
               if (preparedStatement != null) {
                   preparedStatement.setLong(1, fk);
               }
            });
             rs = pst.executeQuery();
            pos = getAllDatabaseResults(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultPositionDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources(rs, pst, conn);
        }
        return pos;
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
            throw new CannotSaveEntityException();
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
    protected String buildInsertQueryWithForeignKeys(PositionDto entity) {
        // check if fk is null
        if (entity.getInstrument() == null || entity.getInstrument().getId() == null) {
            throw new RuntimeException(String.format("Cannot save position %s without an instrument_id", entity));
        }
        // entity.toString() is invoked
        String insertQuery = String.format(INSERT_WITH_FK_SQL, entity.getDataAsString());
        LOGGER.log(Level.INFO, "Built insert query: {0}", insertQuery);

        return insertQuery;
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

}
