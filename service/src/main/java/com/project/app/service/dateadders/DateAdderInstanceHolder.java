package com.project.app.service.dateadders;

import com.project.app.service.exceptions.DateAdderDoesntExistException;
import com.project.app.entities.instrument.frequency.Frequency;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author prafailov
 */
public class DateAdderInstanceHolder {

    private static final Map<String, DateAdder> FREQUENCY_MAP;

    static {
        FREQUENCY_MAP = Map.of(
                Frequency.DAILY.getName(), new DayAdder(),
                Frequency.WEEKLY.getName(), new WeekAdder(),
                Frequency.MONTHLY.getName(), new MonthAdder(),
                Frequency.QUARTERLY.getName(), new MonthAdder(),
                Frequency.SEMI_ANNUALLY.getName(), new MonthAdder(),
                Frequency.ANNUALLY.getName(), new YearAdder());

    }

    private DateAdderInstanceHolder() {}

    public static DateAdder getDateAdder(final Frequency frequency) {
        String freqLowerCase = frequency.getName().toLowerCase();
        DateAdder dateAdder = FREQUENCY_MAP.get(freqLowerCase);
        if (dateAdder == null) {
            throw new DateAdderDoesntExistException();
        }
        return dateAdder;
    }

}
