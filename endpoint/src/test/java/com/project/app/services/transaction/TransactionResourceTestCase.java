package com.project.app.services.transaction;

import com.project.app.businesslogic.transaction.DefaultTransactionBL;
import com.project.app.businesslogic.transaction.TransactionBL;
import com.project.app.entities.transaction.Transaction;
import com.project.app.services.AbstractResourceTestCase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author p.rafailov
 */
public class TransactionResourceTestCase extends AbstractResourceTestCase {

    private final static String TRANSACTION_REST_API_PATH = "transactions";

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
        Transaction tx = tbl.getTransaction(id);
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
        Transaction tx = tbl.getTransaction(id);
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
        return tbl.getAllTransactions().stream().map(Transaction::getId).collect(Collectors.toList());
    }

}
