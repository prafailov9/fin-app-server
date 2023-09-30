package com.project.app.service.validation;

import com.project.app.service.validators.TransactionValidator;
import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Sign;
import com.project.app.entities.transaction.Transaction;
import java.time.LocalDateTime;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class TransactionValidatorTest {

    private Transaction tx;

    @Before
    public void setUp() {
        DepositInstrument ins = new DepositInstrument("asdasdasd", LocalDateTime.now(), LocalDateTime.now(), 0, Frequency.WEEKLY);
        Position pos = new Position(LocalDateTime.MIN, "payer", "receiver", ins, 0);
        tx = new Transaction(Long.MIN_VALUE, LocalDateTime.MIN, 0, Sign.POSITIVE, pos);

    }

    @After
    public void tearDown() {
        tx = null;
    }

    @Test
    public void validateOnCalculationTest() {
        TransactionValidator txVal = new TransactionValidator(tx);
        txVal.onCalculation(tx);
        Transaction validatedTx = txVal.getValidator().getObject();
        assertEquals(tx, validatedTx);
    }

}
