package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.CreditInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author p.rafailov
 */
public class CreditInstrumentConverter extends InstrumentConverter<CreditInstrument, InstrumentDto> {

    @Override
    public InstrumentDto convertToDto(CreditInstrument entity) {
        Timestamp stDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getStartOfPaymentPeriod());
        Timestamp endDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getEndOfPaymentPeriod());

        InstrumentDto dto = new InstrumentDto();

        dto.setId(entity.getId());
        dto.setInstrumentName(entity.getInstrumentName());
        dto.setInterestRate(entity.getInterestRate());
        dto.setInstrumentType("credit");
        dto.setPrincipalFrequency(entity.getPrincipalFrequency().toString());
        dto.setInterestFrequency(entity.getInterestFrequency().toString());
        dto.setPaymentStartingDate(stDate);
        dto.setPaymentEndingDate(endDate);
        return dto;
    }

    @Override
    public CreditInstrument convertToEntity(InstrumentDto dto) {
        LocalDateTime stDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentStartingDate());
        LocalDateTime endDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentEndingDate());
        CreditInstrument inst = new CreditInstrument();

        inst.setId(dto.getId());
        inst.setInstrumentName(dto.getInstrumentName());
        inst.setInterestRate(dto.getInterestRate());
        inst.setStartOfPaymentPeriod(stDate);
        inst.setEndOfPaymentPeriod(endDate);
        inst.setInterestFrequency(Frequency.valueOf(dto.getInterestFrequency()));
        inst.setPrincipalFrequency(Frequency.valueOf(dto.getPrincipalFrequency()));

        return inst;
    }

}
