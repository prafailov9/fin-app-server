package com.project.app.daos.position;

import com.project.app.coredb.AbstractGenericDao;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.exceptions.EntityAlreadyExistsException;
import com.project.app.exceptions.PrepareStatementFailedException;
import com.project.app.factory.DaoInstanceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultPositionDao extends AbstractGenericDao<PositionDto> implements PositionDao {

    protected static final Logger log = LoggerFactory.getLogger(DefaultPositionDao.class);

    private final static String LOAD_ALL_BY_FK_QUERY = "select * from %s where %s=?";
    private final static String INSERT_WITH_FK_SQL = "INSERT INTO positions VALUES (%s)";
    private final InstrumentDao instrumentDao;

    public DefaultPositionDao() {
        super("positions");
        this.instrumentDao = (InstrumentDao) DaoInstanceHolder.get("instrument");
    }

    @Override
    public List<PositionDto> loadAllByInstrumentType(InstrumentDto instrument) {
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
            log.error("Failed to load positions for instrumentId: {}", fk, ex);
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

        InstrumentDto inst = instrumentDao.loadById(fk);
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
            throw new EntityAlreadyExistsException("exists");
        }
        return true;
    }

    @Override
    protected PreparedStatement prepareUpdate(PositionDto entity, Connection connection) throws PrepareStatementFailedException, SQLException {
        String query = "update " + tableName + " set id=?, deal_start_date=?, payer_name=?, "
                + "receiver_name=?, principal=?, position_volume=?, fk_instrument=? where id=" + entity.getId() + ";";
        log.info("Update query: {}", query);

        return initPreparedStatement(query, connection, pst -> {
            pst.setLong(1, entity.getId());
            pst.setTimestamp(2, entity.getDealStartingDate());
            pst.setString(3, entity.getPayer());
            pst.setString(4, entity.getReceiver());
            pst.setDouble(5, entity.getPrincipal());
            pst.setDouble(6, entity.getPositionVolume());
            pst.setLong(7, entity.getInstrument().getId());
        });
    }

    @Override
    protected String buildInsertQueryWithForeignKeys(PositionDto entity) {
        // check if fk is null
        if (entity.getInstrument() == null || entity.getInstrument().getId() == null) {
            throw new RuntimeException(String.format("Cannot save position %s without an instrument_id", entity));
        }

        String insertQuery = String.format(INSERT_WITH_FK_SQL, entity.getDataAsString());
        log.info("Built insert query: {}", insertQuery);

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
