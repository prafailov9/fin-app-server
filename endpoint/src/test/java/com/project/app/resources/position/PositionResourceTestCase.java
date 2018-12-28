package com.project.app.resources.position;

import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.entities.position.Position;
import com.project.app.resources.AbstractResourceTestCase;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author p.rafailov
 */
public class PositionResourceTestCase extends AbstractResourceTestCase {

    private final static String POSITION_REST_API_PATH = "positions";

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
    public void addPositionTest() {
        Position pos = pbl.getPosition(getRandomId());
        pos.setId(null);
        pos.setPayer("new Payer");
        Invocation.Builder ib = getWebTarget().path(POSITION_REST_API_PATH + "/add").request(MediaType.APPLICATION_JSON);
        Position savedPos = ib.post(Entity.entity(pos, MediaType.APPLICATION_JSON), Position.class);

        LOGGER.log(Level.ALL, "Saved position: {0}", savedPos);
        assertNotNull(savedPos.getId());
    }

    @Test
    public void getPositionTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(POSITION_REST_API_PATH + "/" + id).request();
        Position pos = ib.get(Position.class);
        LOGGER.log(Level.INFO, "Retreived position: {0}", pos);
        assertNotNull(pos);
        assertNotNull(pos.getId());
        assertEquals(id, pos.getId());
        assertNotNull(pos.getInstrument());
        assertNotNull(pos.getInstrument().getId());

    }

    @Test
    public void getAllPositionsTest() {
        Invocation.Builder ib = getWebTarget().path(POSITION_REST_API_PATH + "/all").request();
        Response res = ib.get(Response.class);
        List<Position> poss = res.readEntity(new GenericType<List<Position>>() {
        }); // wtf
        LOGGER.log(Level.INFO, "Results count: {0}", poss.size());
        assertNotNull(poss);
    }

    @Test
    public void deletePositionTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(POSITION_REST_API_PATH + "/delete/" + id).request();
        Position deletedPosition = ib.delete(Position.class);
        LOGGER.log(Level.INFO, "Deleted position: {0}", deletedPosition);
        assertNull(deletedPosition.getId());

    }

    @Test
    public void updateInstrumentTest() {
        Long id = getRandomId();
        Position pos = pbl.getPosition(id);
        String newName = "newNameNewName";
        pos.setPayer(newName);
        Invocation.Builder ib = getWebTarget().path(POSITION_REST_API_PATH + "/update").request(MediaType.APPLICATION_JSON);
        Position updatedPos = ib.put(Entity.entity(pos, MediaType.APPLICATION_JSON), Position.class);
        LOGGER.log(Level.INFO, "Updated position: {0}", updatedPos);
        assertEquals(newName, updatedPos.getPayer());
    }

    @Override
    protected List<Long> getAllIds() {
        List<Long> ids = pbl.getAllPositions().stream().map(pos -> pos.getId()).collect(Collectors.toList());
        return ids;
    }

}
