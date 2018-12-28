package com.project.app.businesslogic.dateadders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public abstract class DateAdder {

    public LocalDateTime addToDate(LocalDateTime date, int timeAmount) {
        LocalDateTime nextDate = date.plus(timeAmount, getTimeUnit());
        return nextDate;
    }

    protected abstract ChronoUnit getTimeUnit();

}
