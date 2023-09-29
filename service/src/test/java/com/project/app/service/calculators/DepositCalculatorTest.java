package com.project.app.service.calculators;

import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Sign;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.AbstractServiceTest;
import com.project.app.service.adapters.JsonMapAdapter;
import com.project.app.service.position.DefaultPositionService;
import com.project.app.service.position.PositionService;
import com.project.app.service.results.ResultObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertNotNull;

/**
 *
 * @author prafailov
 */
public class DepositCalculatorTest extends AbstractServiceTest {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private DepositCalculator dc;
    private DepositInstrument dep;
    private Position pos;
    private Transaction tx;
    private PositionService pbl;

    @Before
    public void setUp() {
        dc = new DepositCalculator();
        pbl = new DefaultPositionService();
        dep = new DepositInstrument("depositInstName", LocalDateTime.parse("12/12/2012 12:12:12", DTF),
                LocalDateTime.parse("12/12/2022 12:12:12", DTF), 0.05, Frequency.ANNUALLY);
        pos = new Position(LocalDateTime.parse("12/12/2012 12:12:12", DTF), "somePayer", "someReceiver", dep, 0);
        tx = new Transaction(Long.MIN_VALUE, LocalDateTime.parse("12/12/2012 12:12:12", DTF), 1000, Sign.NEGATIVE, pos);

    }

    @After
    public void tearDown() {
        pbl = null;
    }

//    @Test
    public void calcDepositTest() throws Exception {
        Position pos = pbl.getPosition(1L);
        ResultObject dro = dc.calculateCashFlow(pos);
        assertNotNull(dro);
        JsonMapAdapter adapter = new JsonMapAdapter();
        String json = adapter.marshal(dro.getInterestPayments());
        System.out.println(json);
        assertNotNull(json);
    }

    @Test
    public void depositCalcTest() {
//        Position p = tbl.getTransaction(0L);
//        Map<LocalDateTime, Double> results = dc.calculateCashFlow(t);

    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = pbl.getAllPositions().stream().map(Position::getId).collect(Collectors.toList());
        return ids;
    }

}
