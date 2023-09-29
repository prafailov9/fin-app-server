package com.project.app.service.transaction;

import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.AbstractServiceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static junit.framework.Assert.*;

public class TransactionServiceTest extends AbstractServiceTest {

    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = new DefaultTransactionService();
    }

    @After
    public void tearDown() {
        transactionService = null;
    }

    @Test
    public void getTxTest() {
        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        LOGGER.log(Level.INFO, "Retreived Transaction: {0}", tx);
        assertNotNull(tx);
        assertNotNull(tx.getId());
        assertNotNull(tx.getPosition().getId());
    }

    @Test
    public void getAllTxs() {

        List<Transaction> txs = transactionService.getAllTransactions();
        LOGGER.log(Level.INFO, "Results count: {0}", txs.size());
        assertNotNull(txs);
        assertEquals(txs.size(), getAllIds().size());
    }

    @Test
    public void insertTx() {
        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        tx.setId(null);
        transactionService.insertTransaction(tx);
        LOGGER.log(Level.INFO, "Saved transaction: {0}", tx);
        assertNotNull(tx);
        assertNotNull(tx.getId());
        assertNotNull(tx.getPosition());
        assertNotNull(tx.getPosition().getId());
    }

    @Test
    public void updateTx() {
        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        LocalDateTime newDate = LocalDateTime.of(2012, 12, 12, 12, 12);
        tx.setTransactionDate(newDate);
        transactionService.updateTransaction(tx);
        Transaction newTx = transactionService.getTransaction(tx.getId());
        LOGGER.log(Level.ALL, "Updated transaction: {0}", newTx);
        assertEquals(tx.getTransactionDate().toString(), newTx.getTransactionDate().toString());
    }

    @Test
    public void deleteTx() {
        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        transactionService.deleteTransaction(tx);
        LOGGER.log(Level.INFO, "Deleted transaction: {0}", tx);
        assertEquals(tx.getId(), null);
    }

    @Test
    public void getAllTxsByPosition() {
        Position position = transactionService.getTransaction(getRandomId()).getPosition();
        List<Transaction> txs = transactionService.getAllTransactionsByPosition(position);

        boolean actual = txs.stream().allMatch(tx -> tx.getPosition().equals(position));

        assertTrue(actual);
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = transactionService.getAllTransactions().stream().map(tx -> tx.getId()).collect(Collectors.toList());
        return ids;
    }
}
