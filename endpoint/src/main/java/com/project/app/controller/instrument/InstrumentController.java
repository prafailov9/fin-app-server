package com.project.app.controller.instrument;

import com.project.app.entities.instrument.Instrument;
import com.project.app.service.ServiceHelperUtils;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.instrument.InstrumentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 *
 * @author p.rafailov
 */
@Path("/instruments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController() {
        this.instrumentService = ServiceInstanceHolder.get(ServiceHelperUtils.INSTRUMENT_SERVICE_NAME);
    }

    @GET
    @Path("/{id}")
    public Instrument getInstrument(@PathParam("id") Long id) {
        return instrumentService.getInstrument(id);
    }

    @GET
    @Path("/all")
    public List<Instrument> getAllInstruments() {
        return instrumentService.getAllInstruments();
    }

    @POST
    @Path("/add")
    public Instrument addInstrument(Instrument instrument) {
        instrumentService.insertInstrument(instrument);
        return instrument;
    }

    @PUT
    @Path("/update")
    public Instrument updateInstrument(Instrument instrument) {
        instrumentService.updateInstrument(instrument);
        return instrument;
    }

    @DELETE
    @Path("/delete/{id}")
    public Instrument deleteInstrument(@PathParam("id") Long id) {
        Instrument instrument = instrumentService.getInstrument(id);
        instrumentService.deleteInstrument(instrument);
        return instrument;
    }

}
