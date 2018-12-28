package com.project.app.daos.instrument;

import com.project.app.coredb.GenericDao;
import com.project.app.dtos.instrument.InstrumentDto;
import java.util.List;

/**
 *
 * @author user
 */
public interface InstrumentDao extends GenericDao<InstrumentDto> {

    InstrumentDto loadOneByName(final String name);

    List<InstrumentDto> loadAllByType(final String type);
    
}
