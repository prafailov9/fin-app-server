package com.project.app.service.calculators;

import com.project.app.service.AbstractEntityBLTestCase;
import com.project.app.service.position.DefaultPositionBL;
import com.project.app.service.position.PositionBL;
import com.project.app.service.results.CreditResultObject;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static junit.framework.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class CreditCalculatorTestCase extends AbstractEntityBLTestCase {

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private PositionBL pbl;
    private Calculator<CreditResultObject> calc;

    @Before
    public void setUp() {
        pbl = new DefaultPositionBL();
        calc = new CreditCalculator();
    }

    @After
    public void tearDown() {
        pbl = null;
        calc = null;
    }

    @Test
    public void calculateCreditTest() {

        Position position = pbl.getPosition(18L);

        CreditResultObject cro = (CreditResultObject) calc.calculateCashFlow(position);
        Map<LocalDateTime, Double> prPayments = cro.getPrincipalPayments();
        Map<LocalDateTime, Double> intPayments = cro.getInterestPayments();

        assertNotNull(prPayments);
        assertNotNull(prPayments);

    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = pbl.getAllPositions().stream().map(pos -> pos.getId()).collect(Collectors.toList());
        return ids;
    }

}
