package com.project.app.converters.entityconverters;

import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverterFactory;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author p.rafailov
 */
public class PositionConverter implements EntityConverter<Position, PositionDto> {

    private final InstrumentConverterFactory instrumentConverterFactory;
    private InstrumentConverter<Instrument> instrumentConverter;

    public PositionConverter() {
        instrumentConverterFactory = new InstrumentConverterFactory();
    }

    @Override
    public Position convertToEntity(PositionDto dto) {
        instrumentConverter = instrumentConverterFactory.getConverter(dto.getInstrument().getIntrumentType());
        LocalDateTime ldt = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getDealStartingDate());
        Instrument inst = instrumentConverter.convertToEntity(dto.getInstrument());
        Position pos = new Position();

        pos.setId(dto.getId());
        pos.setPayer(dto.getPayer());
        pos.setReceiver(dto.getReceiver());
        pos.setStartingDateOfDeal(ldt);
        pos.setPositionVolume(dto.getPositionVolume());

        pos.setInstrument(inst);
        return pos;
    }

    @Override
    public PositionDto convertToDto(Position entity) {
        Timestamp tmp = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getStartingDateOfDeal());
        instrumentConverter = instrumentConverterFactory.getConverter(entity.getInstrument().getType());
        PositionDto posDto = new PositionDto();

        posDto.setId(entity.getId());

        posDto.setPositionVolume(entity.getPositionVolume());
        posDto.setDealStartingDate(tmp);
        posDto.setPayer(entity.getPayer());
        posDto.setReceiver(entity.getReceiver());
        posDto.setPrincipal(entity.getPrincipal());
        InstrumentDto instDto = instrumentConverter.convertToDto(entity.getInstrument());
        posDto.setInstrument(instDto);
        return posDto;
    }

}
