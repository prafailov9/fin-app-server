package com.project.app.service.transaction;

import com.project.app.converters.entityconverters.TransactionConverter;
import com.project.app.daos.transaction.TransactionDao;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.exceptions.*;
import com.project.app.factory.DaoInstanceHolder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author p.rafailov
 */
public class DefaultTransactionService implements TransactionService {

    private final static Logger LOGGER = Logger.getLogger(DefaultTransactionService.class.getCanonicalName());

    private final TransactionConverter transactionConverter;
    private final TransactionDao transactionDao;

    public DefaultTransactionService() {
        this.transactionDao = DaoInstanceHolder.get(TransactionDao.class);
        this.transactionConverter = new TransactionConverter();
    }

    @Override
    public void insertTransaction(Transaction transaction) {
        try {
            TransactionDto dto = transactionConverter.convertToDto(transaction);
            transactionDao.save(dto);
            transaction.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (SaveForEntityFailedException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Transaction getTransaction(Long id) {
        TransactionDto dto = transactionDao.loadById(id)
                .orElseThrow(() -> {
                    String message = format("Transaction not found for id: %s", id);
                    LOGGER.log(Level.SEVERE, message);
                    return new NotFoundException(message);
                });
        return transactionConverter.convertToEntity(dto);
    }

    @Override
    public List<Transaction> getAllTransactions() {

        try {
            return transactionDao
                    .loadAll()
                    .stream()
                    .map(transactionConverter::convertToEntity)
                    .collect(Collectors.toList());
        } catch (EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    @Override
    public void deleteTransaction(Transaction transaction) {

        try {
            TransactionDto dto = transactionConverter.convertToDto(transaction);
            transactionDao.delete(dto);
            transaction.setId(null); // null id = not persisted in database.
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try {
            TransactionDto dto = transactionConverter.convertToDto(transaction);
            transactionDao.update(dto);
        } catch (NoSuchEntityException | EntityConverterNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public List<Transaction> getAllTransactionsByPosition(Position position) {
        Long fk = position.getId();
        return transactionDao
                .loadAllByReference(fk)
                .stream()
                .map(transactionConverter::convertToEntity)
                .collect(Collectors.toList());
    }

}
