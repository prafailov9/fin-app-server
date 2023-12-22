package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.converters.entityconverters.EntityConverter;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.Instrument;

import java.sql.Timestamp;

/**
 *
 * @author p.rafailov
 */
public interface InstrumentConverter extends EntityConverter<Instrument, InstrumentDto> {

    @Override
    Instrument convertToEntity(InstrumentDto dto);

    @Override
    InstrumentDto convertToDto(Instrument entity);

}
