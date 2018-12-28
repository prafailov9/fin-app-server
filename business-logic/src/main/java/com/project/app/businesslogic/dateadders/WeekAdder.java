package com.project.app.businesslogic.dateadders;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public class WeekAdder extends DateAdder {

    private final static ChronoUnit TIME_UNIT = ChronoUnit.WEEKS;

    @Override
    protected ChronoUnit getTimeUnit() {
        return TIME_UNIT;
    }

}
