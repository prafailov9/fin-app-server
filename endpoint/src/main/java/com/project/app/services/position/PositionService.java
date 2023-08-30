package com.project.app.services.position;

import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.entities.position.Position;
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
@Path("/positions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionService {

    private final PositionBL positionBL;

    public PositionService() {
        this.positionBL = new DefaultPositionBL();
    }

    @GET
    @Path("/{positionId}")
    public Position getPosition(@PathParam("positionId") Long id) {
        return positionBL.getPosition(id);
    }

    @GET
    @Path("/all")
    public List<Position> getAllPositions() {
        return positionBL.getAllPositions();
    }

    @POST
    @Path("/add")
    public Position addPosition(Position position) {
        positionBL.insertPosition(position);
        return position;
    }

    @PUT
    @Path("/update")
    public Position updatePosition(Position position) {
        positionBL.updatePosition(position);
        return position;
    }

    @DELETE
    @Path("/delete/{id}")
    public Position deletePosition(@PathParam("id") Long id) {
        Position position = positionBL.getPosition(id);
        positionBL.deletePosition(position);
        return position;
    }

}
