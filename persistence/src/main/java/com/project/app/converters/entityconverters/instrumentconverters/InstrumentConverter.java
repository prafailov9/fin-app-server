package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.converters.entityconverters.EntityConverter;

/**
 *
 * @author p.rafailov
 * @param <Instrument>
 * @param <InstrumentDto>
 */
public abstract class InstrumentConverter<Instrument, InstrumentDto> implements EntityConverter<Instrument, InstrumentDto> {

    @Override
    public abstract Instrument convertToEntity(InstrumentDto dto);

    @Override
    public abstract InstrumentDto convertToDto(Instrument entity);

}
