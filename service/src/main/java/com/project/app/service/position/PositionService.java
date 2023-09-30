package com.project.app.service.position;

import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.position.Position;
import com.project.app.service.Service;

import java.util.List;

/**
 *
 * @author p.rafailov
 */
public interface PositionService extends Service {

    void insertPosition(Position position);

    Position getPosition(Long id);

    List<Position> getAllPositions();

    List<Position> getAllPositionsByInstrument(Instrument instrument);
    
    void deletePosition(Position position);

    void updatePosition(Position position);

}
