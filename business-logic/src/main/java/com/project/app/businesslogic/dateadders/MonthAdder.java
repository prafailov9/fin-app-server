package com.project.app.businesslogic.dateadders;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public class MonthAdder extends DateAdder {

    private final static ChronoUnit TIME_UNIT = ChronoUnit.MONTHS;

    @Override
    protected ChronoUnit getTimeUnit() {
        return TIME_UNIT;
    }

}
