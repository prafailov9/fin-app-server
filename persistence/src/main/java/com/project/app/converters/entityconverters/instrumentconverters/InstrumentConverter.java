package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.converters.entityconverters.EntityConverter;
import com.project.app.dtos.instrument.InstrumentDto;

/**
 *
 * @author p.rafailov
 * @param <E>
 * @param <D>
 */
public abstract class InstrumentConverter<E> implements EntityConverter<E, InstrumentDto> {

    @Override
    public abstract E convertToEntity(InstrumentDto dto);

    @Override
    public abstract InstrumentDto convertToDto(E entity);

}
