package com.project.app.service.position;

import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.service.AbstractServiceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static junit.framework.Assert.*;

public class PositionServiceTest extends AbstractServiceTest {

    private PositionService positionService;

    @Before
    public void setUp() {
        positionService = new DefaultPositionService();
    }

    @After
    public void tearDown() {
        positionService = null;
    }

    @Test
    public void getPositionTest() {
        Long id = getRandomId();
        Position pos = positionService.getPosition(id);
        LOGGER.log(Level.INFO, "Retreived position: {0}", pos);
        assertNotNull(pos.getId());
        assertNotNull(pos.getInstrument());
        assertNotNull(pos.getInstrument().getId());
        assertEquals(id, pos.getId());
    }

    @Test
    public void getAllPositionsTest() {
        List<Position> poss = positionService.getAllPositions();
        assertNotNull(poss);
        assertEquals(poss.size(), getAllIds().size());
    }

    @Test
    public void insertPositionTest() {
        Long id = getRandomId();
        Position pos = positionService.getPosition(id);
        pos.setId(null);
        positionService.insertPosition(pos);
        LOGGER.log(Level.INFO, "Saved position: {0}", pos);
        assertNotNull(pos);
        assertNotNull(pos.getId());
        assertNotNull(pos.getInstrument());
        assertNotNull(pos.getInstrument().getId());
    }

    @Test
    public void updatePositionTest() {
        Long id = getRandomId();
        Position pos = positionService.getPosition(id);
        String newPayerName = "new Payer";
        pos.setPayer(newPayerName);
        positionService.updatePosition(pos);
        LOGGER.log(Level.INFO, "Updated position: {0}", pos);
        assertEquals(newPayerName, pos.getPayer());
    }

    @Test
    public void deletePositionTest() {
        Long id = getRandomId();
        Position pos = positionService.getPosition(id);
        positionService.deletePosition(pos);
        LOGGER.log(Level.INFO, "Deleted position: {0}", pos);
        assertNull(pos.getId());
    }

    @Test
    public void getAllPositionsByInstrumentTest() {
        Instrument instrument = positionService.getPosition(getRandomId()).getInstrument();
        List<Position> positionsByInstrument = positionService.getAllPositionsByInstrument(instrument);

        boolean actual = positionsByInstrument.stream().allMatch(pos -> pos.getInstrument().equals(instrument));
        assertTrue(actual);
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = positionService.getAllPositions().stream().map(pos -> pos.getId()).collect(Collectors.toList());
        return ids;
    }
}
