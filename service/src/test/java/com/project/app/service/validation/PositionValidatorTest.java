package com.project.app.service.validation;

import com.project.app.service.validators.PositionValidator;
import com.project.app.entities.instrument.Share;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class PositionValidatorTest {

    private PositionValidator posVal;
    private Position pos;

    @Before
    public void setUp() {
        Share s = new Share();
        pos = new Position(LocalDateTime.MIN, "asda", "adad", s, 0);
        posVal = new PositionValidator(pos);
    }

    @After
    public void tearDown() {
        posVal = null;
        pos = null;
    }

    @Test
    public void validateOnSavePassTest() {

        posVal.onSave(pos);
        Share s = new Share(Long.MIN_VALUE, "ddd", LocalDateTime.MIN, LocalDateTime.MIN);
        Position newPos = new Position(LocalDateTime.MIN, "adadasd", "dasdasdas", s, 0);
        posVal.onSave(newPos);
        assertTrue(posVal.getValidator().getExceptions().isEmpty());

    }

//    @Test(expected = InvalidPositionDuringSaveException.class)
//    public void validateOnSaveFailTest() {
//        pos.setId(null);
//        posVal.onSave();
//    }
}
