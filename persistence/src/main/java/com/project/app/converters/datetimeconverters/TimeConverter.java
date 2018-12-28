package com.project.app.converters.datetimeconverters;

/**
 *
 * @author p.rafailov
 * @param <T>
 * @param <D>
 */
public interface TimeConverter<T, D> {
    
    D convertToDatabaseColumn(T attr);
    
    T convertToEntityAttribute(D dbc);
    
}
