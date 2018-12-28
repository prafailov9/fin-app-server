package com.project.app.businesslogic.transaction;

import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import java.util.List;

/**
 *
 * @author p.rafailov
 */
public interface TransactionBL {

    void insertTransaction(Transaction transaction);

    Transaction getTransaction(Long id);

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByPosition(Position position);

    void deleteTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

}
