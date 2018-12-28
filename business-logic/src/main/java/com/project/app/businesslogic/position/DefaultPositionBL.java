package com.project.app.businesslogic.position;

import com.project.app.converters.entityconverters.PositionConverter;
import com.project.app.daos.position.DefaultPositionDao;
import com.project.app.daos.position.PositionDao;
import com.project.app.dtos.position.PositionDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.exceptions.CannotPersistEntityException;
import com.project.app.exceptions.EntityConverterNotFoundException;
import com.project.app.exceptions.NoRecordFoundException;
import com.project.app.exceptions.NoSuchEntityException;
import com.project.app.exceptions.NullIdException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author p.rafailov
 */
public class DefaultPositionBL implements PositionBL {

    private final static Logger LOGGER = Logger.getLogger(DefaultPositionBL.class.getCanonicalName());

    private final PositionDao positionDao;
    private final PositionConverter positionConverter;

    public DefaultPositionBL() {
        this.positionDao = new DefaultPositionDao();
        this.positionConverter = new PositionConverter();
    }

    @Override
    public void insertPosition(Position position) {
        try {
            PositionDto dto = positionConverter.convertToDto(position);
            positionDao.save(dto);
            position.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (CannotPersistEntityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Position getPosition(Long id) {
        try {

            PositionDto dto = positionDao.loadById(id);
            Position entity = positionConverter.convertToEntity(dto);
            return entity;
        } catch (NoRecordFoundException | NullIdException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Position> getAllPositions() {
        try {
            return positionDao
                    .loadAll()
                    .stream()
                    .map(positionConverter::convertToEntity)
                    .collect(Collectors.toList());
        } catch (EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    @Override
    public void deletePosition(Position position) {
        try {
            PositionDto dto = positionConverter.convertToDto(position);
            positionDao.delete(dto);
            position.setId(null); // null id = not persisted in database.
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void updatePosition(Position position) {
        try {
            PositionDto dto = positionConverter.convertToDto(position);
            positionDao.update(dto);
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public List<Position> getAllPositionsByInstrument(Instrument instrument) {
        List<Position> positionsByInstrument = positionDao.loadAllByReference(instrument.getId()).stream().map(positionConverter::convertToEntity).collect(Collectors.toList());
        return positionsByInstrument;
    }

}
