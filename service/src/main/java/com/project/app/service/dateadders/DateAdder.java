package com.project.app.service.dateadders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public abstract class DateAdder {

    public LocalDateTime addToDate(LocalDateTime date, int timeAmount) {
        return date.plus(timeAmount, getTimeUnit());
    }

    protected abstract ChronoUnit getTimeUnit();

}
