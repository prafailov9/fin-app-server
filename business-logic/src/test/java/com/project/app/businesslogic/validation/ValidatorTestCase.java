package com.project.app.businesslogic.validation;

import com.project.app.businesslogic.validators.GenericValidator;
import com.project.app.businesslogic.exceptions.InvalidEntityDateException;
import com.project.app.entities.instrument.Share;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class ValidatorTestCase {

    private GenericValidator<Position> positionValidator;

    @Before
    public void setUp() {
        LocalDateTime ldt = LocalDateTime.of(2018, 10, 10, 10, 10, 10, 10);
        Share share = new Share("adas", ldt, LocalDateTime.MIN);
        Position pos = new Position(ldt, "adasd", "adads", share, 0);
        positionValidator = GenericValidator.of(pos);
    }

    @After
    public void tearDown() {
        positionValidator = null;
    }

    @Test
    public void validateDateTest() {

        positionValidator.validate((pos) -> {
            return pos.getStartingDateOfDeal()
                    .isEqual(
                            pos.getInstrument()
                                    .getStartOfPaymentPeriod());
        }, new InvalidEntityDateException());
        
        List<Throwable> exc = positionValidator.getExceptions();
        assertTrue(exc.isEmpty());
    }
}
