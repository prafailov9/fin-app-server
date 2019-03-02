package com.project.app.resources.position;

import com.project.app.businesslogic.position.DefaultPositionBL;
import com.project.app.businesslogic.position.PositionBL;
import com.project.app.entities.position.Position;
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
        Position pos = positionBL.getPosition(id);
        return pos;
    }

    @GET
    @Path("/all")
    public List<Position> getAllPositions() {
        List<Position> poss = positionBL.getAllPositions();
        return poss;
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
