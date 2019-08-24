package com.project.app.services.instrument;

import com.project.app.businesslogic.instrument.DefaultInstrumentBL;
import com.project.app.businesslogic.instrument.InstrumentBL;
import com.project.app.entities.instrument.Instrument;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author p.rafailov
 */
@Path("/instruments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentService {

    private final InstrumentBL instrumentBL;

    public InstrumentService() {
        this.instrumentBL = new DefaultInstrumentBL();
    }

    @GET
    @Path("/{id}")
    public Instrument getInstrument(@PathParam("id") Long id) {
        Instrument inst = instrumentBL.getInstrument(id);
        return inst;
    }

    @GET
    @Path("/all")
    public List<Instrument> getAllInstruments() {
        List<Instrument> insts = instrumentBL.getAllInstruments();
        return insts;
    }

    @POST
    @Path("/add")
    public Instrument addInstrument(Instrument instrument) {
        instrumentBL.insertInstrument(instrument);
        return instrument;
    }

    @PUT
    @Path("/update")
    public Instrument updateInstrument(Instrument instrument) {
        instrumentBL.updateInstrument(instrument);
        return instrument;
    }

    @DELETE
    @Path("/delete/{id}")
    public Instrument deleteInstrument(@PathParam("id") Long id) {
        Instrument instrument = instrumentBL.getInstrument(id);
        instrumentBL.deleteInstrument(instrument);
        return instrument;
    }

}
