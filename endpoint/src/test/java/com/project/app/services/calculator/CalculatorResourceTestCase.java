package com.project.app.services.calculator;

import com.project.app.businesslogic.instrument.DefaultInstrumentBL;
import com.project.app.businesslogic.instrument.InstrumentBL;
import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.services.AbstractResourceTestCase;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.ws.rs.client.Invocation;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class CalculatorResourceTestCase extends AbstractResourceTestCase {

    private final static String CALCULATOR_RA_PATH = "calculator";
    private PositionBL pbl;
    private InstrumentBL ibl;

    @Before
    public void setUp() {
        pbl = new DefaultPositionBL();
        ibl = new DefaultInstrumentBL();
    }

    @After
    public void tearDown() {
        pbl = null;
        ibl = null;
    }

    @Test
    public void getDepositResultObjectsTest() {
//        Long posId = getRandomPositionIdByInstrumentType("deposit");
        long posId = 15L;
        Invocation.Builder ib = getWebTarget().path(CALCULATOR_RA_PATH + "/deposit-calc/" + posId).request();
        ResultObject dro = ib.get(ResultObject.class);

        assertNotNull(dro);

    }

    @Test
    public void getCreditResultObjectTest() {
//        Long posId = getRandomPositionIdByInstrumentType("credit");
        long posId = 18L;
        Invocation.Builder ib = getWebTarget().path(CALCULATOR_RA_PATH + "/credit-calc/" + posId).request();
        ResultObject dro = ib.get(ResultObject.class);
        System.out.println(dro.getInterestPayments());
        assertNotNull(dro);
    }

    @Override
    protected List<Long> getAllIds() {
        return pbl.getAllPositions().stream().map(Position::getId).collect(Collectors.toList());
    }

    // Not finished
    private Long getRandomPositionIdByInstrumentType(String type) {
        List<Long> ids = getAllIdsByInstrumentType(type);
        return ids.get(getGenerator().nextInt(ids.size()));
    }

    // Not finished
    private List<Long> getAllIdsByInstrumentType(String type) {
        List<Instrument> instruments = ibl.getAllInstrumentsByType(type);
        return pbl
                .getAllPositionsByInstrument(instruments.get(getGenerator().nextInt(0, instruments.size())))
                .stream()
                .map(Position::getId)
                .collect(Collectors.toList());
    }

}
