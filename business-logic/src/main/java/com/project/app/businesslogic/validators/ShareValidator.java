package com.project.app.businesslogic.validators;

import com.project.app.entities.instrument.Share;
import java.time.LocalDateTime;

/**
 *
 * @author prafailov
 */
public class ShareValidator extends InstrumentValidator<Share> {

    public ShareValidator(Share entity) {
        super(entity);
    }

    @Override
    protected Long getEntityId(Share entity) {
        return entity.getId();
    }

    @Override
    protected Pair<LocalDateTime, LocalDateTime> getStartEndDates(Share entity) {
        return Pair.createPair(entity.getStartOfPaymentPeriod(), entity.getEndOfPaymentPeriod());
    }

    @Override
    protected String getInstrumentName(Share entity) {
        return entity.getInstrumentName();
    }

}
