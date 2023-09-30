package com.project.app.service;

import com.project.app.service.calculators.CalculationService;
import com.project.app.service.calculators.DefaultCalculationService;
import com.project.app.service.instrument.DefaultInstrumentService;
import com.project.app.service.instrument.InstrumentService;
import com.project.app.service.position.DefaultPositionService;
import com.project.app.service.position.PositionService;
import com.project.app.service.transaction.DefaultTransactionService;
import com.project.app.service.transaction.TransactionService;

import java.util.Map;


final public class ServiceInstanceHolder {

    private static final Map<String, Service> SERVICE_MAP;

    static {
        SERVICE_MAP = Map.of(
                CalculationService.class.getName(), new DefaultCalculationService(),
                InstrumentService.class.getName(), new DefaultInstrumentService(),
                PositionService.class.getName(), new DefaultPositionService(),
                TransactionService.class.getName(), new DefaultTransactionService());
    }

    private ServiceInstanceHolder() {

    }

    public static <T extends Service> T get(final Class<T> type) {
        Service service = SERVICE_MAP.get(type.getName());
        if (service == null) {
            throw new RuntimeException("Service doesn't exist for type: " + type.getName());
        }
        return type.cast(service);
    }


}
