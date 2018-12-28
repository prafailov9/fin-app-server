package com.project.app.businesslogic.validators;

import com.project.app.entities.instrument.Share;
import java.time.LocalDateTime;
import javafx.util.Pair;

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
        Pair<LocalDateTime, LocalDateTime> datesPair
                = new Pair<>(entity.getStartOfPaymentPeriod(), entity.getEndOfPaymentPeriod());
        return datesPair;
    }

    @Override
    protected String getInstrumentName(Share entity) {
        return entity.getInstrumentName();
    }

}
