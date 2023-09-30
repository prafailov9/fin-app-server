package com.project.app.controller.calculator;


import com.project.app.entities.position.Position;
import com.project.app.service.ServiceHelperUtils;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.calculators.CalculationService;
import com.project.app.service.position.PositionService;
import com.project.app.service.results.ResultObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author prafailov
 */
@Path("/calculator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculationController {

    private final PositionService positionService;
    private final CalculationService calculationService;

    public CalculationController() {
        positionService = ServiceInstanceHolder.get(PositionService.class);
        calculationService = ServiceInstanceHolder.get(CalculationService.class);
    }

    @GET
    @Path("/deposit-calc/{posId}")
    public ResultObject getDepositCalculationResults(@PathParam("posId") Long posId) {
        Position pos = positionService.getPosition(posId);

        return calculationService.getDepositCalculationResults(pos);
    }

    @GET
    @Path("/credit-calc/{posId}")
    public ResultObject getCreditCalculationResults(@PathParam("posId") Long posId) {
        Position pos = positionService.getPosition(posId);

        return calculationService.getCreditCalculationResults(pos);
    }

}
