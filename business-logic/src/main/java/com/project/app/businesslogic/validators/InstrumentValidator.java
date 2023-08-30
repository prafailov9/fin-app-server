package com.project.app.businesslogic.validators;

import com.project.app.businesslogic.exceptions.calcvalidation.InvalidInstrumentStateBeforeCalculationException;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class InstrumentValidator<Instrument> extends CrudValidator<Instrument> implements CalculationValidator<Instrument> {

    public InstrumentValidator(Instrument entity) {
        super(entity);
    }

    @Override
    public void onSave(Instrument entity) {
        getValidator().setObject(entity);
        Long id = getEntityId(getValidator().getObject());
        Pair<LocalDateTime, LocalDateTime> datesPair = getStartEndDates(getValidator().getObject());
        getValidator().validate(ins -> Objects.nonNull(id) && datesPair.getKey().isBefore(datesPair.getValue()),
                new NullPointerException());
    }

    @Override
    public void onDelete(Instrument entity) {
        getValidator().setObject(entity);
        makePersistenceCheck();
    }

    @Override
    public void onUpdate(Instrument entity) {
        getValidator().setObject(entity);
        makePersistenceCheck();
    }

    @Override
    public void onGet(Instrument entity) {
        getValidator().setObject(entity);
        makePersistenceCheck();
    }

    @Override
    public void onCalculation(Instrument entity) {
        getValidator().setObject(entity);
        Pair<LocalDateTime, LocalDateTime> datesPair = getStartEndDates(entity);

        getValidator().validate(obj -> datesPair.getKey().isBefore(datesPair.getValue())
                && !getInstrumentName(entity).equals(""), new InvalidInstrumentStateBeforeCalculationException());
    }

    @Override
    protected abstract Long getEntityId(Instrument entity);

    protected abstract String getInstrumentName(Instrument entity);

    protected abstract Pair<LocalDateTime, LocalDateTime> getStartEndDates(Instrument entity);
}
