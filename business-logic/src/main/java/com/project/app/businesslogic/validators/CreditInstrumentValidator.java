package com.project.app.businesslogic.validators;

import com.project.app.businesslogic.exceptions.calcvalidation.InvalidInstrumentStateBeforeCalculationException;
import com.project.app.entities.instrument.CreditInstrument;
import java.time.LocalDateTime;
/**
 *
 * @author prafailov
 */
public class CreditInstrumentValidator extends InstrumentValidator<CreditInstrument> {

    public CreditInstrumentValidator(CreditInstrument entity) {
        super(entity);
    }

    @Override
    public void onSave(CreditInstrument entity) {
        super.onSave(entity);
//        getValidator();
    }

    @Override
    protected Long getEntityId(CreditInstrument entity) {
        return entity.getId();
    }

    @Override
    protected Pair<LocalDateTime, LocalDateTime> getStartEndDates(CreditInstrument entity) {
        return Pair.createPair(entity.getStartOfPaymentPeriod(), entity.getEndOfPaymentPeriod());
    }

    @Override
    public void onCalculation(CreditInstrument entity) {
        super.onCalculation(entity);
        getValidator()
                .validate(en -> entity.getInterestRate() > 0 && entity.getInterestRate() < 1,
                new InvalidInstrumentStateBeforeCalculationException());

    }

    @Override
    protected String getInstrumentName(CreditInstrument entity) {
        return entity.getInstrumentName();
    }

}
