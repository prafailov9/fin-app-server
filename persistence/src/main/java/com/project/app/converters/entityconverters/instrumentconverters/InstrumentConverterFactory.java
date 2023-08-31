package com.project.app.converters.entityconverters.instrumentconverters;

import com.project.app.converters.entityconverters.instrumentconverters.CreditInstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.DepositInstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.ShareConverter;
import com.project.app.exceptions.EntityConverterNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author p.rafailov
 */
public class InstrumentConverterFactory {

    private static final Map<String, InstrumentConverter> CONVERTER_MAP = createConverterMap();

    private static Map<String, InstrumentConverter> createConverterMap() {
        Map<String, InstrumentConverter> map = new HashMap<>();
        map.put("credit", new CreditInstrumentConverter());
        map.put("deposit", new DepositInstrumentConverter());
        map.put("share", new ShareConverter());
        return map;
    }

    public InstrumentConverter getConverter(final String key) throws EntityConverterNotFoundException {
        try {
            InstrumentConverter converter = Objects.requireNonNull(CONVERTER_MAP.get(key));
            return converter;
        } catch (NullPointerException ex) {
            throw new EntityConverterNotFoundException(key);
        }
    }

}
