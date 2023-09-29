package com.project.app.service.dateadders;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author prafailov
 */
public class YearAdder extends DateAdder {

    private final static ChronoUnit TIME_UNIT = ChronoUnit.YEARS;

    @Override
    protected ChronoUnit getTimeUnit() {
        return TIME_UNIT;
    }

}
