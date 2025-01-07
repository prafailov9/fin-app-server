package com.project.app.service.position;

import com.project.app.converters.entityconverters.PositionConverter;
import com.project.app.daos.position.PositionDao;
import com.project.app.dtos.position.PositionDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.exceptions.*;
import com.project.app.factory.DaoInstanceHolder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author p.rafailov
 */
public class DefaultPositionService implements PositionService {

    private final static Logger LOGGER = Logger.getLogger(DefaultPositionService.class.getCanonicalName());

    private final PositionDao positionDao;
    private final PositionConverter positionConverter;

    public DefaultPositionService() {
        this.positionDao = DaoInstanceHolder.get(PositionDao.class);
        this.positionConverter = new PositionConverter();
    }

    @Override
    public void insertPosition(Position position) {
        try {
            PositionDto dto = positionConverter.convertToDto(position);
            positionDao.save(dto);
            position.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (SaveForEntityFailedException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Position getPosition(Long id) {
        PositionDto dto = positionDao.loadById(id)
                .orElseThrow(() -> {
                    String message = format("Position not found for id: %s", id);
                    LOGGER.log(Level.SEVERE, message);
                    return new NotFoundException(message);
                });

        return positionConverter.convertToEntity(dto);
    }

    @Override
    public List<Position> getAllPositions() {
        List<PositionDto> dtos = positionDao.loadAll();
        if (dtos.isEmpty()) {
            throw new NotFoundException("No positions found!");
        }
        return dtos.stream()
                .map(positionConverter::convertToEntity)
                .collect(toList());
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
        return positionDao.loadAllByReference(instrument.getId())
                .stream()
                .map(positionConverter::convertToEntity)
                .collect(toList());
    }

}
