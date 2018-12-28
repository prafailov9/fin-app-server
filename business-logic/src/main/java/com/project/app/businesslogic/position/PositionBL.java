package com.project.app.businesslogic.position;

import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import java.util.List;

/**
 *
 * @author p.rafailov
 */
public interface PositionBL {

    void insertPosition(Position position);

    Position getPosition(Long id);

    List<Position> getAllPositions();

    List<Position> getAllPositionsByInstrument(Instrument instrument);
    
    void deletePosition(Position position);

    void updatePosition(Position position);

}
