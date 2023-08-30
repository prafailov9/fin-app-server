package com.project.app.businesslogic.validators;

import com.project.app.businesslogic.exceptions.calcvalidation.InvalidInstrumentStateBeforeCalculationException;
import com.project.app.entities.instrument.DepositInstrument;
import java.time.LocalDateTime;

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
        return Pair.createPair(entity.getStartOfPaymentPeriod(), entity.getEndOfPaymentPeriod());
    }

    @Override
    public void onCalculation(DepositInstrument entity) {
        super.onCalculation(entity);
        getValidator().validate(
                en -> entity.getInterestRate() > 0 && entity.getInterestRate() < 1,
                new InvalidInstrumentStateBeforeCalculationException());
    }

    @Override
    protected String getInstrumentName(DepositInstrument entity) {
        return entity.getInstrumentName();
    }

}
