package com.project.app.businesslogic.transaction;

import com.project.app.converters.entityconverters.TransactionConverter;
import com.project.app.daos.transaction.DefaultTransactionDao;
import com.project.app.daos.transaction.TransactionDao;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.exceptions.CannotPersistEntityException;
import com.project.app.exceptions.EntityConverterNotFoundException;
import com.project.app.exceptions.NoRecordFoundException;
import com.project.app.exceptions.NoSuchEntityException;
import com.project.app.exceptions.NullIdException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author p.rafailov
 */
public class DefaultTransactionBL implements TransactionBL {

    private final static Logger LOGGER = Logger.getLogger(DefaultTransactionBL.class.getCanonicalName());

    private final TransactionConverter transactionConverter;
    private final TransactionDao transactionDao;

    public DefaultTransactionBL() {
        this.transactionDao = new DefaultTransactionDao();
        this.transactionConverter = new TransactionConverter();
    }

    @Override
    public void insertTransaction(Transaction transaction) {
        try {
            TransactionDto dto = transactionConverter.convertToDto(transaction);
            transactionDao.save(dto);
            transaction.setId(dto.getId()); // non-null id = persisted in the database.
        } catch (CannotPersistEntityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public Transaction getTransaction(Long id) {
        try {
            TransactionDto dto = transactionDao.loadById(id);
            Transaction entity = transactionConverter.convertToEntity(dto);
            return entity;
        } catch (NoRecordFoundException | NullIdException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
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
        List<Transaction> txs = transactionDao
                .loadAllByReference(fk)
                .stream()
                .map(transactionConverter::convertToEntity)
                .collect(Collectors.toList());
        return txs;
    }

}
