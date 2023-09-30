package com.project.app.service;

import com.project.app.service.calculators.DefaultCalculationService;
import com.project.app.service.instrument.DefaultInstrumentService;
import com.project.app.service.position.DefaultPositionService;
import com.project.app.service.transaction.DefaultTransactionService;

import java.util.Map;

import static com.project.app.service.ServiceHelperUtils.*;

final public class ServiceInstanceHolder {

    private static final Map<String, Service> SERVICE_MAP;

    static {
        SERVICE_MAP = Map.of(
                CALCULATION_SERVICE_NAME, new DefaultCalculationService(),
                INSTRUMENT_SERVICE_NAME, new DefaultInstrumentService(),
                POSITION_SERVICE_NAME, new DefaultPositionService(),
                TRANSACTION_SERVICE_NAME, new DefaultTransactionService());
    }

    public static <T extends Service> T get(final String key) {
        Service service = SERVICE_MAP.get(key);
        if (service == null) {
            throw new RuntimeException();
        }

        return (T) service;
    }

}
