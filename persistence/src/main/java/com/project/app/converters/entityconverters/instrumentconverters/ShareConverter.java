package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.Share;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author p.rafailov
 */
public class ShareConverter implements InstrumentConverter {

    // share: instrumentName, startOfPaymentPeriod, endOfPaymentPeriod
    @Override
    public Share convertToEntity(InstrumentDto dto) {
        LocalDateTime stDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentStartingDate());
        LocalDateTime endDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentEndingDate());

        Share entity = new Share();
        entity.setId(dto.getId());
        entity.setInstrumentName(dto.getInstrumentName());
        entity.setStartOfPaymentPeriod(stDate);
        entity.setEndOfPaymentPeriod(endDate);

        return entity;
    }

    @Override
    public InstrumentDto convertToDto(Instrument entity) {
        return convert((Share) entity);
    }

    private InstrumentDto convert(Share entity) {
        Timestamp stDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getStartOfPaymentPeriod());
        Timestamp endDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getEndOfPaymentPeriod());
        InstrumentDto dto = new InstrumentDto();

        dto.setId(entity.getId());
        dto.setInstrumentName(entity.getInstrumentName());
        dto.setPaymentStartingDate(stDate);
        dto.setPaymentEndingDate(endDate);
        dto.setInstrumentType("share");

        return dto;
    }

}
