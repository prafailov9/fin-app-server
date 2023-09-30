package com.project.app.converters;

import com.project.app.converters.entityconverters.TransactionConverter;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.Share;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Sign;
import com.project.app.entities.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TransactionConverterTest {

    private TransactionConverter transactionConverter;
    private Transaction tx;
    private TransactionDto txDto;

    @Before
    public void setUp() {
        transactionConverter = new TransactionConverter();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse("12/12/2012 12:12:12", dtf);
        LocalDateTime endDate = LocalDateTime.parse("12/12/2022 12:12:12", dtf);

        Instrument ins = new Share("shareInst", startDate, endDate);
        InstrumentDto insDto = new InstrumentDto("shareinst", 0, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate),
                null, null, "share");
        Position pos = new Position(startDate, "sp", "sr", ins, 100);
        tx = new Transaction(Long.MIN_VALUE, startDate, 0, Sign.POSITIVE, pos);
        PositionDto posDto = new PositionDto(Timestamp.valueOf(startDate), "payer", "receiver", 0, 0, insDto);
        tx = new Transaction(1L, startDate, 0, Sign.POSITIVE, pos);
        txDto = new TransactionDto(Timestamp.valueOf(startDate), 0, -1, posDto);

    }

    @Test
    public void convertToDtoTest() {
        TransactionDto dto = transactionConverter.convertToDto(tx);

        LocalDateTime dtoDate = dto.getTransactionDate().toLocalDateTime();
        assertEquals(tx.getId(), dto.getId());
        assertEquals(tx.getTransactionDate(), dtoDate);
        assertEquals(tx.getAmount(), dto.getAmount(), 0.000000);
    }

    @Test
    public void convertToEntity() {
        Transaction entity = transactionConverter.convertToEntity(txDto);
        LocalDateTime dtoDate = txDto.getTransactionDate().toLocalDateTime();
        LocalDateTime entityDate = entity.getTransactionDate();

        assertEquals(txDto.getId(), entity.getId());
        assertEquals(dtoDate, entityDate);
        assertEquals(txDto.getAmount(), entity.getAmount(), 0.000000);
    }

}
