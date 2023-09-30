package com.project.app.service.transaction;

import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.Service;

import java.util.List;

/**
 *
 * @author p.rafailov
 */
public interface TransactionService extends Service {

    void insertTransaction(Transaction transaction);

    Transaction getTransaction(Long id);

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByPosition(Position position);

    void deleteTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

}
