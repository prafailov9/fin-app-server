package com.project.app.services.calculator;

import com.project.app.businesslogic.calculators.DefaultCalculationBL;
import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.position.Position;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author prafailov
 */
@Path("/calculator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculationService {

    private final PositionBL pbl;
    private final DefaultCalculationBL cbl;

    public CalculationService() {
        pbl = new DefaultPositionBL();
        cbl = new DefaultCalculationBL();
    }

    @GET
    @Path("/deposit-calc/{posId}")
    public ResultObject getDepositCalculationResults(@PathParam("posId") Long posId) {
        Position pos = pbl.getPosition(posId);

        ResultObject dro = cbl.getDepositCalculationResults(pos);
        return dro;
    }

    @GET
    @Path("/credit-calc/{posId}")
    public ResultObject getCreditCalculationResults(@PathParam("posId") Long posId) {
        Position pos = pbl.getPosition(posId);

        return cbl.getCreditCalculationResults(pos);
    }

}
