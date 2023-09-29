package com.project.app.service.instrument;

import com.project.app.service.exceptions.EntityValidationException;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverterFactory;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author p.rafailov
 */
public class DefaultInstrumentBL implements InstrumentBL {

    private final static Logger LOGGER = Logger.getLogger(DefaultInstrumentBL.class.getCanonicalName());

    private final InstrumentDao instrumentDao;
    private InstrumentConverter<Instrument, InstrumentDto> instrumentConverter;
    private final InstrumentConverterFactory converterFactory;

    public DefaultInstrumentBL() {
        this.instrumentDao = new DefaultInstrumentDao();
        this.converterFactory = new InstrumentConverterFactory();
    }

    @Override
    public void insertInstrument(Instrument instrument) {
        try {
            instrumentConverter = converterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.save(dto);
            instrument.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (SaveForEntityFailedException | EntityValidationException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Instrument getInstrument(Long id) {
        Instrument instrument = null;
        try {
            InstrumentDto dto = instrumentDao.loadById(id);
            instrumentConverter = converterFactory.getConverter(dto.getIntrumentType());
            instrument = instrumentConverter.convertToEntity(dto);
        } catch (NoRecordFoundException | NullIdException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        } finally {
            return instrument;
        }
    }

    @Override
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        try {
            instrumentDao
                    .loadAll()
                    .stream()
                    .forEach(dto -> {
                        instrumentConverter = converterFactory.getConverter(dto.getIntrumentType());
                        Instrument entity = instrumentConverter.convertToEntity(dto);
                        instruments.add(entity);
                    });
        } catch (EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return instruments;
    }

    @Override
    public void deleteInstrument(Instrument instrument) {
        try {
            instrumentConverter = converterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.delete(dto);
            instrument.setId(null); // null id = not persisted in database.
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void updateInstrument(Instrument instrument) {
        try {
            instrumentConverter = converterFactory.getConverter(instrument.getType());
            InstrumentDto dto = instrumentConverter.convertToDto(instrument);
            instrumentDao.update(dto);
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public List<Instrument> getAllInstrumentsByType(String type) {
        instrumentConverter = converterFactory.getConverter(type);
        List<Instrument> instruments = instrumentDao.loadAllByType(type).stream().map(instrumentConverter::convertToEntity).collect(Collectors.toList());
        return instruments;
    }

}
