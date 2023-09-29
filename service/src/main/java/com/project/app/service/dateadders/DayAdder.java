package com.project.app.service.dateadders;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public class DayAdder extends DateAdder {

    private final static ChronoUnit TIME_UNIT = ChronoUnit.DAYS;

    @Override
    protected ChronoUnit getTimeUnit() {
        return TIME_UNIT;
    }

}
