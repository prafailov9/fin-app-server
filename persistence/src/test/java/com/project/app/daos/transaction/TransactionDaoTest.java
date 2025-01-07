package com.project.app.daos.transaction;

import com.project.app.coredb.AbstractGenericDaoTest;
import com.project.app.dtos.transaction.TransactionDto;
import com.project.app.exceptions.EntityAlreadyExistsException;
import com.project.app.exceptions.SaveForEntityFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.*;

/**
 * @author p.rafailov
 */
public class TransactionDaoTest extends AbstractGenericDaoTest<TransactionDto> {

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
        TransactionDto tx = loadTransaction(id);
        tx.setId(null);
        LOGGER.log(Level.INFO, "Saving tx dto... {0}", tx.toString());
        txDao.save(tx);
        LOGGER.log(Level.INFO, "assigned id: {0}", tx.getId());
        assertNotNull(tx.getId());
    }

    /*
        Test should throw exception to pass.
     */
    @Test(expected = SaveForEntityFailedException.class)
    public void saveTransactionDtoWithNoFKTest() {
        LOGGER.log(Level.INFO, "In Save tx fail-test...");
        Long id = getRandomId();
        TransactionDto tx = loadTransaction(id);
        tx.setPosition(null);
        tx.setId(null);
        txDao.save(tx);
    }

    /*
    Test should throw exception to pass
     */
    @Test(expected = EntityAlreadyExistsException.class)
    public void saveTransactionDtoWithExistingIdTest() {
        LOGGER.log(Level.INFO, "In Save tx existing id fail-test...");
        TransactionDto tx = loadTransaction(getRandomId());
        txDao.save(tx);
    }

    @Test
    public void loadByIdTest() {
        Long id = getRandomId();
        TransactionDto tx = loadTransaction(id);
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
        TransactionDto tx = loadTransaction(id);
        txDao.delete(tx);
        LOGGER.log(Level.INFO, "Deleted record: {0}", tx);
        assertNull(tx.getId());
    }

    @Test
    public void updateTest() {
        Long id = getRandomId();
        TransactionDto tx = loadTransaction(id);
        double amount = 12312.0;
        tx.setAmount(amount);
        txDao.update(tx);
        TransactionDto updatedTx = loadTransaction(tx.getId());
        LOGGER.log(Level.INFO, "Updated record: {0}", updatedTx.toString());
        assertEquals(amount, updatedTx.getAmount(), 0.0d);
    }

    @Test
    public void loadAllByFKTest() {
        Long fk = loadTransaction(getRandomId()).getPosition().getId();
        List<TransactionDto> txs = txDao.loadAllByReference(fk);
        boolean actual = txs.stream().allMatch(tx -> tx.getPosition().getId().equals(fk));
        assertTrue(actual);
    }

    private TransactionDto loadTransaction(Long id) {
        Optional<TransactionDto> opt = txDao.loadById(id);
        assertTrue(opt.isPresent());

        return opt.get();
    }

    @Override
    protected List<TransactionDto> getRecords() {
        return txDao.loadAll();
    }

    @Override
    protected Long getDtoId(TransactionDto dto) {
        return dto.getId();
    }

}
