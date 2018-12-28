package com.project.app.businesslogic.transaction;

import com.project.app.businesslogic.AbstractEntityBLTestCase;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionBLTestCase extends AbstractEntityBLTestCase {

    private TransactionBL tbl;

    @Before
    public void setUp() {
        tbl = new DefaultTransactionBL();
    }

    @After
    public void tearDown() {
        tbl = null;
    }

    @Test
    public void getTxTest() {
        Long id = getRandomId();
        Transaction tx = tbl.getTransaction(id);
        LOGGER.log(Level.INFO, "Retreived Transaction: {0}", tx);
        assertNotNull(tx);
        assertNotNull(tx.getId());
        assertNotNull(tx.getPosition().getId());
    }

    @Test
    public void getAllTxs() {

        List<Transaction> txs = tbl.getAllTransactions();
        LOGGER.log(Level.INFO, "Results count: {0}", txs.size());
        assertNotNull(txs);
        assertEquals(txs.size(), getAllIds().size());
    }

    @Test
    public void insertTx() {
        Long id = getRandomId();
        Transaction tx = tbl.getTransaction(id);
        tx.setId(null);
        tbl.insertTransaction(tx);
        LOGGER.log(Level.INFO, "Saved transaction: {0}", tx);
        assertNotNull(tx);
        assertNotNull(tx.getId());
        assertNotNull(tx.getPosition());
        assertNotNull(tx.getPosition().getId());
    }

    @Test
    public void updateTx() {
        Long id = getRandomId();
        Transaction tx = tbl.getTransaction(id);
        LocalDateTime newDate = LocalDateTime.of(2012, 12, 12, 12, 12);
        tx.setTransactionDate(newDate);
        tbl.updateTransaction(tx);
        Transaction newTx = tbl.getTransaction(tx.getId());
        LOGGER.log(Level.ALL, "Updated transaction: {0}", newTx);
        assertEquals(tx.getTransactionDate().toString(), newTx.getTransactionDate().toString());
    }

    @Test
    public void deleteTx() {
        Long id = getRandomId();
        Transaction tx = tbl.getTransaction(id);
        tbl.deleteTransaction(tx);
        LOGGER.log(Level.INFO, "Deleted transaction: {0}", tx);
        assertEquals(tx.getId(), null);
    }

    @Test
    public void getAllTxsByPosition() {
        Position position = tbl.getTransaction(getRandomId()).getPosition();
        List<Transaction> txs = tbl.getAllTransactionsByPosition(position);

        boolean actual = txs.stream().allMatch(tx -> tx.getPosition().equals(position));

        assertTrue(actual);
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = tbl.getAllTransactions().stream().map(tx -> tx.getId()).collect(Collectors.toList());
        return ids;
    }
}
