package com.project.app.service.instrument;

import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverterFactory;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.exceptions.*;
import com.project.app.factory.DaoInstanceHolder;
import com.project.app.service.exceptions.EntityValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author p.rafailov
 */
public class DefaultInstrumentService implements InstrumentService {

    private final static Logger LOGGER = Logger.getLogger(DefaultInstrumentService.class.getCanonicalName());

    private final InstrumentDao instrumentDao;
    private InstrumentConverter instrumentConverter;

    public DefaultInstrumentService() {
        this.instrumentDao = DaoInstanceHolder.get(InstrumentDao.class);
    }

    @Override
    public void insertInstrument(Instrument instrument) {
        try {
            instrumentConverter = InstrumentConverterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.save(dto);
            instrument.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (SaveForEntityFailedException | EntityValidationException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Instrument getInstrument(Long id) {
        Instrument instrument;
        try {
            InstrumentDto dto = instrumentDao.loadById(id);
            instrumentConverter = InstrumentConverterFactory.getConverter(dto.getIntrumentType());
            instrument = instrumentConverter.convertToEntity(dto);
        } catch (NoRecordFoundException | NullIdException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
        return instrument;
    }

    @Override
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        try {
            instrumentDao
                    .loadAll()
                    .forEach(dto -> {
                        instrumentConverter = InstrumentConverterFactory.getConverter(dto.getIntrumentType());
                        Instrument entity = instrumentConverter.convertToEntity(dto);
                        instruments.add(entity);
                    });
        } catch (EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
        return instruments;
    }

    @Override
    public void deleteInstrument(Instrument instrument) {
        try {
            instrumentConverter = InstrumentConverterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.delete(dto);
            instrument.setId(null); // null id = not persisted in database.
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void updateInstrument(Instrument instrument) {
        try {
            instrumentConverter = InstrumentConverterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.update(dto);
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Instrument> getAllInstrumentsByType(String type) {
        instrumentConverter = InstrumentConverterFactory.getConverter(type);
        return instrumentDao.loadAllByType(type)
                .stream()
                .map(instrumentConverter::convertToEntity)
                .collect(Collectors.toList());
    }

}
