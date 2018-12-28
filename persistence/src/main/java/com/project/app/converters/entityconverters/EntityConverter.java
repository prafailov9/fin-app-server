package com.project.app.converters.entityconverters;

import com.project.app.converters.datetimeconverters.LocalDateTimeToTimestampConverter;

/**
 *
 * @author p.rafailov
 * @param <E> - entity
 * @param <D> - data transfer object
 */
public interface EntityConverter<E, D> {

    E convertToEntity(D dto);

    D convertToDto(E entity);

    default LocalDateTimeToTimestampConverter getDateTimePersistenceConverter() {
        return new LocalDateTimeToTimestampConverter();
    }
}
