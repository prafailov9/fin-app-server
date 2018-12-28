package com.project.app.businesslogic.validators;

import com.project.app.businesslogic.exceptions.calcvalidation.InvalidInstrumentStateBeforeCalculationException;
import com.project.app.entities.instrument.DepositInstrument;
import java.time.LocalDateTime;
import javafx.util.Pair;

/**
 *
 * @author prafailov
 */
public class DepositInstrumentValidator extends InstrumentValidator<DepositInstrument> {

    public DepositInstrumentValidator(DepositInstrument entity) {
        super(entity);
    }

    @Override
    public void onSave(DepositInstrument entity) {
        super.onSave(entity);
    }

    @Override
    protected Long getEntityId(DepositInstrument entity) {
        return entity.getId();
    }

    @Override
    protected Pair<LocalDateTime, LocalDateTime> getStartEndDates(DepositInstrument entity) {
        Pair<LocalDateTime, LocalDateTime> datesPair
                = new Pair<>(entity.getStartOfPaymentPeriod(), entity.getEndOfPaymentPeriod());
        return datesPair;
    }

    @Override
    public void onCalculation(DepositInstrument entity) {
        super.onCalculation(entity);
        getValidator().validate(
                en -> {
                    return entity.getInterestRate() > 0 && entity.getInterestRate() < 1;
                },
                new InvalidInstrumentStateBeforeCalculationException());
    }

    @Override
    protected String getInstrumentName(DepositInstrument entity) {
        return entity.getInstrumentName();
    }

}
