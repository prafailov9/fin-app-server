package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author p.rafailov
 */
public class DepositInstrumentConverter extends InstrumentConverter<DepositInstrument, InstrumentDto> {

    @Override
    public InstrumentDto convertToDto(DepositInstrument entity) {
        Timestamp stDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getStartOfPaymentPeriod());
        Timestamp endDate = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getEndOfPaymentPeriod());

        InstrumentDto dto = new InstrumentDto();
        dto.setId(entity.getId());
        dto.setInstrumentName(entity.getInstrumentName());
        dto.setInterestRate(entity.getInterestRate());
        dto.setInterestFrequency(entity.getInterestFrequency().toString());
        dto.setPaymentEndingDate(endDate);
        dto.setPaymentStartingDate(stDate);
        dto.setInstrumentType("deposit");

        return dto;
    }

    @Override
    public DepositInstrument convertToEntity(InstrumentDto dto) {
        LocalDateTime stDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentStartingDate());
        LocalDateTime endDate = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getPaymentEndingDate());
        DepositInstrument inst = new DepositInstrument();

        inst.setId(dto.getId());
        inst.setInstrumentName(dto.getInstrumentName());
        inst.setEndOfPaymentPeriod(endDate);
        inst.setInterestRate(dto.getInterestRate());
        inst.setInterestFrequency(Frequency.valueOf(dto.getInterestFrequency()));
        inst.setStartOfPaymentPeriod(stDate);

        return inst;
    }

}
