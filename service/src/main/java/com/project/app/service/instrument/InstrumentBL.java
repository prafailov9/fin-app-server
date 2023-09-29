package com.project.app.service.instrument;

import com.project.app.entities.instrument.Instrument;
import java.util.List;

/**
 *
 * @author p.rafailov
 */
public interface InstrumentBL {

    void insertInstrument(Instrument instrument);

    Instrument getInstrument(Long id);

    List<Instrument> getAllInstruments();

    List<Instrument> getAllInstrumentsByType(String type);

    void deleteInstrument(Instrument instrument);

    void updateInstrument(Instrument instrument);
}
