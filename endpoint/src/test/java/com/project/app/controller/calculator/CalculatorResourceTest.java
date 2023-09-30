package com.project.app.controller.calculator;

import com.project.app.controller.AbstractResourceTest;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.instrument.InstrumentService;
import com.project.app.service.position.PositionService;
import com.project.app.service.results.ResultObject;
import jakarta.ws.rs.client.Invocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

/**
 *
 * @author prafailov
 */
public class CalculatorResourceTest extends AbstractResourceTest {

    private final static String CALCULATOR_RA_PATH = "calculator";
    private PositionService positionService;
    private InstrumentService instrumentService;

    @Before
    public void setUp() {
        positionService = ServiceInstanceHolder.get(PositionService.class);
        instrumentService = ServiceInstanceHolder.get(InstrumentService.class);
    }

    @After
    public void tearDown() {
        positionService = null;
        instrumentService = null;
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
        return positionService.getAllPositions().stream().map(Position::getId).collect(Collectors.toList());
    }

    // Not finished
    private Long getRandomPositionIdByInstrumentType(String type) {
        List<Long> ids = getAllIdsByInstrumentType(type);
        return ids.get(getGenerator().nextInt(ids.size()));
    }

    // Not finished
    private List<Long> getAllIdsByInstrumentType(String type) {
        List<Instrument> instruments = instrumentService.getAllInstrumentsByType(type);
        return positionService
                .getAllPositionsByInstrument(instruments.get(getGenerator().nextInt(0, instruments.size())))
                .stream()
                .map(Position::getId)
                .collect(Collectors.toList());
    }

}
