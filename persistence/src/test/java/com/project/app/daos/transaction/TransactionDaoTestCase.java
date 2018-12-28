package com.project.app.daos.transaction;

import com.project.app.coredb.AbstractGenericDaoTestCase;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.exceptions.CannotPersistEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author p.rafailov
 */
public class TransactionDaoTestCase extends AbstractGenericDaoTestCase {

    private DefaultTransactionDao txDao;

    @Before
    public void setUp() {
        txDao = new DefaultTransactionDao();
    }

    @After
    public void tearDown() {
        txDao = null;
    }

    @Test
    public void saveTest() {
        LOGGER.log(Level.INFO, "In Save tx test...");
        Long id = getRandomId();
        TransactionDto tx = txDao.loadById(id);
        tx.setId(null);
        LOGGER.log(Level.INFO, "Saving tx dto... {0}", tx.toString());
        txDao.save(tx);
        LOGGER.log(Level.INFO, "assigned id: {0}", tx.getId());
        assertNotNull(tx.getId());
    }

    /*
        Test should throw exception to pass.
     */
    @Test(expected = CannotPersistEntityException.class)
    public void saveTransactionDtoWithNoFKTest() {
        LOGGER.log(Level.INFO, "In Save tx fail-test...");
        TransactionDto tx = txDao.loadById(getRandomId());
        tx.setPosition(null);
        tx.setId(null);
        txDao.save(tx);
    }

    /*
    Test should throw exception to pass
     */
    @Test(expected = CannotPersistEntityException.class)
    public void saveTransactionDtoWithExistingIdTest() {
        LOGGER.log(Level.INFO, "In Save tx existing id fail-test...");
        TransactionDto tx = txDao.loadById(getRandomId());
        txDao.save(tx);
    }

    @Test
    public void loadByIdTest() {
        Long id = getRandomId();
        TransactionDto tx = txDao.loadById(id);
        LOGGER.log(Level.INFO, "Loaded record: {0}", tx.toString());
        assertNotNull(tx);
    }

    @Test
    public void loadAllTest() {
        List<TransactionDto> txList = txDao.loadAll();
        LOGGER.log(Level.INFO, "size of list: {0}", txList.size());
        assertNotNull(txList);
    }

    @Test
    public void deleteTest() {
        Long id = getRandomId();
        TransactionDto tx = txDao.loadById(id);
        txDao.delete(tx);
        LOGGER.log(Level.INFO, "Deleted record: {0}", tx);
        assertNull(tx.getId());
    }

    @Test
    public void updateTest() {
        Long id = getRandomId();
        TransactionDto tx = txDao.loadById(id);
        double amount = 12312.0;
        tx.setAmount(amount);
        txDao.update(tx);
        TransactionDto updatedTx = txDao.loadById(tx.getId());
        LOGGER.log(Level.INFO, "Updated record: {0}", updatedTx.toString());
        assertEquals(amount, updatedTx.getAmount(), 0.0d);
    }

    @Test
    public void loadAllByFKTest() {
        Long fk = txDao.loadById(getRandomId()).getPosition().getId();
        List<TransactionDto> txs = txDao.loadAllByReference(fk);
        boolean actual = txs.stream().allMatch(tx -> tx.getPosition().getId().equals(fk));
        assertTrue(actual);
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = txDao.loadAll().stream().map(tx -> tx.getId()).collect(Collectors.toList());
        return ids;
    }

}
