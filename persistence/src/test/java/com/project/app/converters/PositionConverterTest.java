package com.project.app.converters;

import com.project.app.converters.entityconverters.PositionConverter;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.entities.instrument.CreditInstrument;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class PositionConverterTest {

    private PositionConverter posConv;
    private Position posEntity;
    private PositionDto posDto;

    @Before
    public void setUp() {
        Instrument ins = new CreditInstrument(3L, "adadad", LocalDateTime.MIN, LocalDateTime.MIN, 0, Frequency.ANNUALLY, Frequency.ANNUALLY);
        InstrumentDto insDto = new InstrumentDto("adadad", 0, Timestamp.valueOf(LocalDateTime.MIN),
                Timestamp.valueOf(LocalDateTime.MIN), Frequency.ANNUALLY.toString(), Frequency.ANNUALLY.toString(), "credit");
        posEntity = new Position(LocalDateTime.MIN, "dasda", "asdasd", ins, 0);
        posConv = new PositionConverter();
        posDto = new PositionDto(Timestamp.valueOf(LocalDateTime.MIN), "dasda", "asdasd", 0, 0, insDto);
    }

    @After
    public void tearDown() {
        posConv = null;
        posEntity = null;
        posDto = null;
    }

    @Test
    public void convertToDtoTest() {
        PositionDto dto = posConv.convertToDto(posEntity);
        System.out.println(dto.getInstrument());
        assertNotNull(dto);
    }

    @Test
    public void convertToEntityTest() {
        Position entity = posConv.convertToEntity(posDto);
        assertNotNull(entity);
        assertEquals(entity.getId(), posDto.getId());
    }

}
