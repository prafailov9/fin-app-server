package com.project.app.converters;

import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverterFactory;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.CreditInstrument;
import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.instrument.Share;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author p.rafailov
 */
public class InstrumentConverterTestCase {

    private InstrumentConverter<Instrument, InstrumentDto> conv;
    private InstrumentConverterFactory factory;

    private InstrumentDto creditDto;
    private InstrumentDto depositDto;
    private InstrumentDto shareDto;
    private CreditInstrument creditEntity;
    private DepositInstrument depositEntity;
    private Share shareEntity;

    @Before
    public void setUp() {
        factory = new InstrumentConverterFactory();
        creditDto = new InstrumentDto("creditDto", 100, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                Frequency.valueOf(Frequency.ANNUALLY.toString()).toString(), Frequency.valueOf(Frequency.MONTHLY.toString()).toString(), "credit");
        depositDto = new InstrumentDto("depositDto", 0, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), Frequency.valueOf(Frequency.MONTHLY.toString()).toString(), null, "deposit");
        shareDto = new InstrumentDto("shareDto", 0, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), null, null, "share");
        creditEntity = new CreditInstrument(1L, "creditEntity", LocalDateTime.MIN, LocalDateTime.MIN, 0, Frequency.ANNUALLY, Frequency.ANNUALLY);
        depositEntity = new DepositInstrument(3L, "depositEntity", LocalDateTime.MIN, LocalDateTime.MIN, 0, Frequency.ANNUALLY);
        shareEntity = new Share(Long.MIN_VALUE, "shareEntity", LocalDateTime.MIN, LocalDateTime.MIN);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void convertCreditEntityTest() {
//        setInstrumentConverter(getMatcher().getConverter(creditDto.getIntrumentType()));
//        CreditInstrument entity = (CreditInstrument) getInstrumentConverter().convertToEntity(creditDto);

        conv = factory.getConverter(creditDto.getIntrumentType());
        CreditInstrument entity = (CreditInstrument) conv.convertToEntity(creditDto);
        assertNotNull(entity.getInstrumentName());
        assertNotNull(entity.getStartOfPaymentPeriod());
        assertNotNull(entity.getInterestRate());
        assertNotNull(entity.getInterestFrequency());
        assertNotNull(entity.getPrincipalFrequency());
    }

    @Test
    public void convertDepositEntityTest() {

//        setInstrumentConverter(getMatcher().getConverter(depositDto.getIntrumentType()));
//        CreditInstrument entity = (CreditInstrument) getInstrumentConverter().convertToEntity(depositDto);
        conv = factory.getConverter(depositDto.getIntrumentType());
        DepositInstrument entity = (DepositInstrument) conv.convertToEntity(depositDto);

        assertNotNull(entity.getInstrumentName());
        assertNotNull(entity.getEndOfPaymentPeriod());
        assertNotNull(entity.getStartOfPaymentPeriod());
        assertNotNull(entity.getInterestFrequency());
    }

    @Test
    public void convertShareEntityTest() {
//        setInstrumentConverter(getMatcher().getConverter(shareDto.getIntrumentType())); 
//        Share entity = (Share) getInstrumentConverter().convertToEntity(shareDto);

        conv = factory.getConverter(shareDto.getIntrumentType());
        Share entity = (Share) conv.convertToEntity(shareDto);

        assertNotNull(entity.getEndOfPaymentPeriod());
        assertNotNull(entity.getInstrumentName());
        assertNotNull(entity.getStartOfPaymentPeriod());
    }

    @Test
    public void convertCreditDtoTest() {
        // no fields can be null
//        setInstrumentConverter(getMatcher().getConverter(creditEntity.getClass().getSimpleName()));
//        InstrumentDto dto = (InstrumentDto) getInstrumentConverter().convertToDto(creditEntity);

        conv = factory.getConverter(creditEntity.getType());
        InstrumentDto dto = (InstrumentDto) conv.convertToDto(creditEntity);

        assertNotNull(dto.getInstrumentName());
        assertNotNull(dto.getPaymentEndingDate());
        assertNotNull(dto.getPaymentStartingDate());
        assertNotNull(dto.getInterestRate());
        assertNotNull(dto.getInterestFrequency());
        assertNotNull(dto.getPrincipalFrequency());
    }

    @Test
    public void convertDepositDtoTest() {

        // prFreq, intRate must be null, type must be "deposit"
//        setInstrumentConverter(getMatcher().getConverter(depositEntity.getClass().getSimpleName()));
//        InstrumentDto dto = (InstrumentDto) getInstrumentConverter().convertToDto(depositEntity);
        conv = factory.getConverter(depositEntity.getType());
        InstrumentDto dto = (InstrumentDto) conv.convertToDto(depositEntity);

        assertNull(dto.getPrincipalFrequency());
        assertEquals(0.0, dto.getInterestRate(), 0.0);
        assertEquals("deposit", dto.getIntrumentType());
    }

    @Test
    public void convertShareDtoTest() {
//        setInstrumentConverter(getMatcher().getConverter(shareEntity.getClass().getSimpleName()));
//        InstrumentDto dto = (InstrumentDto) getInstrumentConverter().convertToDto(shareEntity);
        conv = factory.getConverter(shareEntity.getType());
        InstrumentDto dto = (InstrumentDto) conv.convertToDto(shareEntity);

        assertNull(dto.getInterestFrequency());
        assertNull(dto.getPrincipalFrequency());
        assertEquals(0.0, dto.getInterestRate(), 0.0);
        assertEquals("share", dto.getIntrumentType());

    }

}
