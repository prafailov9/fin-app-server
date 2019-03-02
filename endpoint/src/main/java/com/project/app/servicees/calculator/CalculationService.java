package com.project.app.servicees.calculator;

import com.project.app.businesslogic.calculators.DefaultCalculationBL;
import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.position.Position;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

        ResultObject ro = cbl.getCreditCalculationResults(pos);
        return ro;
    }

}
