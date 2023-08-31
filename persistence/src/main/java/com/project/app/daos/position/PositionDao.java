package com.project.app.daos.position;

import com.project.app.coredb.GenericDao;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import java.util.List;

public interface PositionDao extends GenericDao<PositionDto> {
    
    List<PositionDto> loadAllByReference(Long fk);
    
    List<PositionDto> loadAllByInstrumentType(InstrumentDto instrument);
    
}
