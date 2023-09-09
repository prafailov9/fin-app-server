package com.project.app.businesslogic.dateadders;

import com.project.app.businesslogic.exceptions.DateAdderDoesntExistException;
import com.project.app.entities.instrument.frequency.Frequency;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author prafailov
 */
public class DateAdderFactory {

    private static final Map<String, DateAdder> FREQUENCY_MAP = createFrequencyMap();

    private static Map<String, DateAdder> createFrequencyMap() {
        Map<String, DateAdder> map;
        map = new HashMap<>();
        map.put("daily", new DayAdder());
        map.put("weekly", new WeekAdder());
        map.put("monthly", new MonthAdder());
        map.put("quaterly", new MonthAdder());
        map.put("semi-annually", new MonthAdder());
        map.put("annually", new YearAdder());
        return map;
    }

    public DateAdder getDateAdder(final Frequency frequency) {
        String freqLowerCase = frequency.getFreq().toLowerCase();
        try {
            DateAdder adder = Objects.requireNonNull(FREQUENCY_MAP.get(freqLowerCase));
            return adder;
        } catch (NullPointerException ex) {
            throw new DateAdderDoesntExistException();
        }
    }

}
