package com.project.app.controller.transaction;

import com.project.app.controller.AbstractResourceTest;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.ServiceHelperUtils;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.transaction.TransactionService;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 *
 * @author p.rafailov
 */
public class TransactionResourceTest extends AbstractResourceTest {

    private final static String TRANSACTION_REST_API_PATH = "transactions";

    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = ServiceInstanceHolder.get(TransactionService.class);
    }

    @After
    public void tearDown() {
        transactionService = null;
    }

    @Test
    public void getTransactionTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(TRANSACTION_REST_API_PATH + "/" + id).request();
        Transaction tx = ib.get(Transaction.class);
        LOGGER.log(Level.INFO, "Retreived transaction: {0}", tx);
        assertNotNull(tx);
        assertNotNull(tx.getId());
        assertNotNull(tx.getPosition());
        assertNotNull(tx.getPosition().getId());

    }

    @Test
    public void getAllTransactionsTest() {

        Invocation.Builder ib = getWebTarget().path(TRANSACTION_REST_API_PATH + "/all").request();
        Response res = ib.get();
        List<Transaction> txs = res.readEntity(new GenericType<>() {
        }); // wtf
        LOGGER.log(Level.INFO, "Retreived list size: {0}", txs.size());
        assertNotNull(txs);

    }

    @Test
    public void addTransactionTest() {
        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        tx.setId(null);
        tx.setTransactionDate(LocalDateTime.now());
        Invocation.Builder ib = getWebTarget().path(TRANSACTION_REST_API_PATH + "/add").request(MediaType.APPLICATION_JSON);
        Transaction newTx = ib.post(Entity.entity(tx, MediaType.APPLICATION_JSON), Transaction.class);
        LOGGER.log(Level.INFO, "Added transaction entity: {0}", newTx.toString());
        assertNotNull(newTx);
        assertNotNull(newTx.getId());

    }

    @Test
    public void updateTransactionTest() {

        Long id = getRandomId();
        Transaction tx = transactionService.getTransaction(id);
        LocalDateTime newDate = LocalDateTime.of(2018, 10, 18, 12, 12);
        tx.setTransactionDate(newDate);

        Invocation.Builder ib = getWebTarget().path(TRANSACTION_REST_API_PATH + "/update").request(MediaType.APPLICATION_JSON);
        Transaction updatedTx = ib.put(Entity.entity(tx, MediaType.APPLICATION_JSON), Transaction.class);
        LOGGER.log(Level.INFO, " New Tx Date: {0}", updatedTx.getTransactionDate().toString());
        assertEquals(newDate, updatedTx.getTransactionDate());
    }

    @Test
    public void deleteTransactionTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(TRANSACTION_REST_API_PATH + "/delete/" + id).request();
        Transaction tx = ib.delete(Transaction.class);
        LOGGER.log(Level.INFO, "Deleted transaction: {0}", tx.toString());
        assertNull(tx.getId());
    }

    @Override
    protected List<Long> getAllIds() {
        return transactionService.getAllTransactions().stream().map(Transaction::getId).collect(Collectors.toList());
    }

}
