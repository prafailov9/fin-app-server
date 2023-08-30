package com.project.app.services.instrument;

import com.project.app.businesslogic.instrument.DefaultInstrumentBL;
import com.project.app.businesslogic.instrument.InstrumentBL;
import com.project.app.entities.instrument.Instrument;
import com.project.app.services.AbstractResourceTestCase;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
public class InstrumentResourceTestCase extends AbstractResourceTestCase {

    private final static String INSTRUMENT_REST_API_PATH = "instruments";

    private InstrumentBL ibl;

    @Before
    public void setUp() {
        ibl = new DefaultInstrumentBL();
    }

    @After
    public void tearDown() {
        ibl = null;
    }

    @Test
    public void getInstrumentTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(INSTRUMENT_REST_API_PATH + "/" + id).request();
        Instrument inst = ib.get(Instrument.class);
        LOGGER.log(Level.INFO, "Received instrument: {0}", inst);
        assertNotNull(inst);
        assertNotNull(inst.getId());
        assertEquals(id, inst.getId());
    }

    @Test
    public void getAllInstrumentsTest() {
        String resource = "/all";
        Invocation.Builder ib = getWebTarget().path(INSTRUMENT_REST_API_PATH + resource).request();
        List<Instrument> entities = ib.get(new GenericType<>() {
        });

        LOGGER.log(Level.INFO, "Results count: {0}", entities.size());
        assertNotNull(entities);
    }

    @Test
    public void addInstrumentTest() {

        Long id = getRandomId();
        Instrument inst = ibl.getInstrument(id);
        inst.setId(null);
        Invocation.Builder ib = getWebTarget().path(INSTRUMENT_REST_API_PATH + "/add").request(MediaType.APPLICATION_JSON);
        Instrument savedInstrument = ib.post(Entity.entity(inst, MediaType.APPLICATION_JSON), Instrument.class);
        LOGGER.log(Level.INFO, "Saved Instrument: {0}", savedInstrument);
        assertNotNull(savedInstrument);
        assertNotNull(savedInstrument.getId());
    }

    @Test
    public void deleteInstrumentTest() {
        Long id = getRandomId();
        Invocation.Builder ib = getWebTarget().path(INSTRUMENT_REST_API_PATH + "/delete/" + id).request();
        Instrument deletedInst = ib.delete(Instrument.class);
        LOGGER.log(Level.INFO, "Deleted instrument: {0]", deletedInst);
        assertNull(deletedInst.getId());
    }

    @Test
    public void updateInstrumentTest() {
        Long id = getRandomId();
        Instrument inst = ibl.getInstrument(id);
        String newName = "updatedName";
        inst.setInstrumentName(newName);

        Invocation.Builder ib = getWebTarget().path(INSTRUMENT_REST_API_PATH + "/update").request(MediaType.APPLICATION_JSON);
        Response res = ib.put(Entity.entity(inst, MediaType.APPLICATION_JSON));
        Instrument updatedInst = res.readEntity(new GenericType<>() {
        });
        LOGGER.log(Level.INFO, "Updated instrument: {0]", updatedInst);
        assertEquals(newName, updatedInst.getInstrumentName());
    }

    @Override
    protected List<Long> getAllIds() {
        return ibl.getAllInstruments().stream().map(Instrument::getId).collect(Collectors.toList());
    }

}
