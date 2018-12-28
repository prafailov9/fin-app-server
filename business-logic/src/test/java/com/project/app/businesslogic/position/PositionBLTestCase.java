package com.project.app.businesslogic.position;

import com.project.app.businesslogic.AbstractEntityBLTestCase;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionBLTestCase extends AbstractEntityBLTestCase {

    private PositionBL pbl;

    @Before
    public void setUp() {
        pbl = new DefaultPositionBL();
    }

    @After
    public void tearDown() {
        pbl = null;
    }

    @Test
    public void getPositionTest() {
        Long id = getRandomId();
        Position pos = pbl.getPosition(id);
        LOGGER.log(Level.INFO, "Retreived position: {0}", pos);
        assertNotNull(pos.getId());
        assertNotNull(pos.getInstrument());
        assertNotNull(pos.getInstrument().getId());
        assertEquals(id, pos.getId());
    }

    @Test
    public void getAllPositionsTest() {
        List<Position> poss = pbl.getAllPositions();
        assertNotNull(poss);
        assertEquals(poss.size(), getAllIds().size());
    }

    @Test
    public void insertPositionTest() {
        Long id = getRandomId();
        Position pos = pbl.getPosition(id);
        pos.setId(null);
        pbl.insertPosition(pos);
        LOGGER.log(Level.INFO, "Saved position: {0}", pos);
        assertNotNull(pos);
        assertNotNull(pos.getId());
        assertNotNull(pos.getInstrument());
        assertNotNull(pos.getInstrument().getId());
    }

    @Test
    public void updatePositionTest() {
        Long id = getRandomId();
        Position pos = pbl.getPosition(id);
        String newPayerName = "new Payer";
        pos.setPayer(newPayerName);
        pbl.updatePosition(pos);
        LOGGER.log(Level.INFO, "Updated position: {0}", pos);
        assertEquals(newPayerName, pos.getPayer());
    }

    @Test
    public void deletePositionTest() {
        Long id = getRandomId();
        Position pos = pbl.getPosition(id);
        pbl.deletePosition(pos);
        LOGGER.log(Level.INFO, "Deleted position: {0}", pos);
        assertNull(pos.getId());
    }

    @Test
    public void getAllPositionsByInstrumentTest() {
        Instrument instrument = pbl.getPosition(getRandomId()).getInstrument();
        List<Position> positionsByInstrument = pbl.getAllPositionsByInstrument(instrument);

        boolean actual = positionsByInstrument.stream().allMatch(pos -> pos.getInstrument().equals(instrument));
        assertTrue(actual);
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = pbl.getAllPositions().stream().map(pos -> pos.getId()).collect(Collectors.toList());
        return ids;
    }
}
