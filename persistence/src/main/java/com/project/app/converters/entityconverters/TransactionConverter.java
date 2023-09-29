package com.project.app.converters.entityconverters;

import com.project.app.dtos.position.PositionDto;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Sign;
import com.project.app.entities.transaction.Transaction;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author p.rafailov
 */
public class TransactionConverter implements EntityConverter<Transaction, TransactionDto> {

    private final PositionConverter positionConverter;

    public TransactionConverter() {
        positionConverter = new PositionConverter();
    }

    @Override
    public Transaction convertToEntity(TransactionDto dto) {
        LocalDateTime ldt = getDateTimePersistenceConverter().convertToEntityAttribute(dto.getTransactionDate());
        Position pos = positionConverter.convertToEntity(dto.getPosition());

        Transaction tx = new Transaction();
        
        tx.setId(dto.getId());
        tx.setTransactionDate(ldt);
        tx.setAmount(dto.getAmount());
        tx.setSign(Sign.valueOf(dto.getSign()));
        tx.setPosition(pos);

        return tx;
    }

    @Override
    public TransactionDto convertToDto(Transaction entity) {
        Timestamp tmp = getDateTimePersistenceConverter().convertToDatabaseColumn(entity.getTransactionDate());
        PositionDto posDto = positionConverter.convertToDto(entity.getPosition());

        TransactionDto txDto = new TransactionDto();
        
        txDto.setId(entity.getId());
        txDto.setPosition(posDto);
        txDto.setTransactionDate(tmp);
        txDto.setAmount(entity.getAmount());
        txDto.setSign(entity.getSign().getValue());

        return txDto;
    }

}
