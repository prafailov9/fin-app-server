package com.project.app.converters.datetimeconverters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author p.rafailov
 */
public class LocalDateTimeToTimestampConverter implements TimeConverter<LocalDateTime, Timestamp> {

    @Override
    public final Timestamp convertToDatabaseColumn(LocalDateTime entityAttribute) {
        return Timestamp.valueOf(entityAttribute);
    }

    @Override
    public final LocalDateTime convertToEntityAttribute(Timestamp databaseColumn) {
        return databaseColumn.toLocalDateTime();
    }

}
