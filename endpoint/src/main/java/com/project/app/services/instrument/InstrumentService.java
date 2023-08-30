package com.project.app.services.instrument;

import com.project.app.businesslogic.instrument.DefaultInstrumentBL;
import com.project.app.businesslogic.instrument.InstrumentBL;
import com.project.app.entities.instrument.Instrument;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
        return instrumentBL.getInstrument(id);
    }

    @GET
    @Path("/all")
    public List<Instrument> getAllInstruments() {
        return instrumentBL.getAllInstruments();
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
