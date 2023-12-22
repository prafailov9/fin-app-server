package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.converters.entityconverters.instrumentconverters.CreditInstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.DepositInstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.ShareConverter;
import com.project.app.entities.instrument.Instrument;
import com.project.app.exceptions.EntityConverterNotFoundException;

import java.util.*;

/**
 * @author p.rafailov
 */
public class InstrumentConverterFactory {

    private static final Map<String, InstrumentConverter> CONVERTER_MAP;

    static {
        CONVERTER_MAP = Map.of("credit", new CreditInstrumentConverter(),
                "deposit", new DepositInstrumentConverter(),
                "share", new ShareConverter());
    }


    public static InstrumentConverter getConverter(final String key) throws EntityConverterNotFoundException {
        InstrumentConverter converter = CONVERTER_MAP.get(key);
        if (converter == null) {
            throw new EntityConverterNotFoundException(key);
        }
        return converter;
    }

}
