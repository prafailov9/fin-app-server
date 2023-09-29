package com.project.app.service.calculators;

import com.project.app.entities.position.Position;
import com.project.app.service.AbstractServiceTest;
import com.project.app.service.position.DefaultPositionService;
import com.project.app.service.position.PositionService;
import com.project.app.service.results.CreditResultObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertNotNull;

/**
 *
 * @author prafailov
 */
public class CreditCalculatorTest extends AbstractServiceTest {

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private PositionService positionService;
    private Calculator<CreditResultObject> calculator;

    @Before
    public void setUp() {
        positionService = new DefaultPositionService();
        calculator = new CreditCalculator();
    }

    @After
    public void tearDown() {
        positionService = null;
        calculator = null;
    }

    @Test
    public void calculateCreditTest() {

        Position position = positionService.getPosition(18L);

        CreditResultObject cro = (CreditResultObject) calculator.calculateCashFlow(position);
        Map<LocalDateTime, Double> prPayments = cro.getPrincipalPayments();
        Map<LocalDateTime, Double> intPayments = cro.getInterestPayments();

        assertNotNull(prPayments);
        assertNotNull(prPayments);

    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = positionService.getAllPositions().stream().map(pos -> pos.getId()).collect(Collectors.toList());
        return ids;
    }

}
