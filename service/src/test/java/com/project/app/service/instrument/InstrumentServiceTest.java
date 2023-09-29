package com.project.app.service.instrument;

import com.project.app.entities.instrument.CreditInstrument;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Sign;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.AbstractServiceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author p.rafailov
 */
public class InstrumentServiceTest extends AbstractServiceTest {

    private InstrumentService instrumentService;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Before
    public void setUp() {
        instrumentService = new DefaultInstrumentService();
    }

    @After
    public void tearDown() {
        instrumentService = null;
    }

    @Test
    public void insertInstrumentTest() {
        Instrument entity = new CreditInstrument("creditEntity", LocalDateTime.now(), LocalDateTime.now(),
                0, Frequency.ANNUALLY, Frequency.ANNUALLY);
        instrumentService.insertInstrument(entity);
        LOGGER.log(Level.INFO, "Assigned ID on inserted instrument: {0}", entity.getId());
        assertNotNull(entity.getId());
    }

    @Test
    public void getInstrumentTest() {
        Instrument inst = instrumentService.getInstrument(getRandomId());

        LOGGER.log(Level.INFO, "Retreived instrument: {0}", inst);
        assertNotNull(inst);
        assertNotNull(inst.getId());

    }

    @Test
    public void removeInstrumentTest() {
        Long id = getRandomId();
        Instrument in = instrumentService.getInstrument(id);
        instrumentService.deleteInstrument(in);
        LOGGER.log(Level.INFO, "Deleted Instrument", in);
        assertNull(in.getId());
    }

    @Test
    public void getAllInstrumentsTestCase() {
        List<Instrument> list = instrumentService.getAllInstruments();
        assertNotNull(list);
        assertEquals(list.size(), getAllIds().size());
    }

    @Test
    public void updateInstrumentTest() {
        Long id = getRandomId();
        Instrument in = instrumentService.getInstrument(id);
        String newName = "updatedName";
        in.setInstrumentName(newName);
        instrumentService.updateInstrument(in);
        Instrument upIn = instrumentService.getInstrument(id);
        LOGGER.log(Level.INFO, "Updated instrument: {0}", upIn);
        assertEquals(in.getInstrumentName(), upIn.getInstrumentName());
    }

    public void calcCreditTest() {
        CreditInstrument cr = new CreditInstrument("", LocalDateTime.parse("12/12/2012 12:12:12", DTF),
                LocalDateTime.parse("12/12/2022 12:12:12", DTF), 0, Frequency.MONTHLY, Frequency.ANNUALLY);
        Position pos = new Position(LocalDateTime.parse("12/12/2012 12:12:12", DTF), "somePayer", "someReceiver", cr, 0.0);
        Transaction tx = new Transaction(Long.MIN_VALUE, LocalDateTime.parse("12/12/2012 12:12:12", DTF), 1000, Sign.POSITIVE, pos);

        cr.getStartOfPaymentPeriod();
        cr.getEndOfPaymentPeriod();
        cr.getInterestFrequency();
        cr.getPrincipalFrequency();
        tx.getAmount();
        cr.getInterestRate();
        Period diff = Period.between(
                cr.getStartOfPaymentPeriod().toLocalDate().withDayOfMonth(1),
                cr.getEndOfPaymentPeriod().toLocalDate().withDayOfMonth(1));
        System.out.println(diff); //P3M
        int months = diff.getMonths();// months calc (calc ONCE)
        int numberOfPayments = months / cr.getPrincipalFrequency().getOrdinal(); // calc ONCE
        double principal = tx.getAmount() / numberOfPayments; // calc ONCE

        double principalPayment = tx.getAmount() - pos.getPrincipal();
        principalPayment = principalPayment - pos.getPrincipal();
        double interest = cr.getInterestRate() * principalPayment;
        // after principal payment interest is re-calculated wih current debt(current principla payment);
    }

    @Override
    protected List<Long> getAllIds() {
        return instrumentService.getAllInstruments().stream().map(ins -> ins.getId()).collect(Collectors.toList());
    }

}
