package com.project.app.controller.position;

import com.project.app.entities.position.Position;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.position.PositionService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 *
 * @author p.rafailov
 */
@Path("/positions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {

    private final PositionService positionService;

    public PositionController() {
        this.positionService = ServiceInstanceHolder.get(PositionService.class);
    }

    @GET
    @Path("/{positionId}")
    public Position getPosition(@PathParam("positionId") Long id) {
        return positionService.getPosition(id);
    }

    @GET
    @Path("/all")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @POST
    @Path("/add")
    public Position addPosition(Position position) {
        positionService.insertPosition(position);
        return position;
    }

    @PUT
    @Path("/update")
    public Position updatePosition(Position position) {
        positionService.updatePosition(position);
        return position;
    }

    @DELETE
    @Path("/delete/{id}")
    public Position deletePosition(@PathParam("id") Long id) {
        Position position = positionService.getPosition(id);
        positionService.deletePosition(position);
        return position;
    }

}
