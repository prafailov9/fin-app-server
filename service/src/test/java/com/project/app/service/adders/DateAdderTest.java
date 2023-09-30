package com.project.app.service.adders;

import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.service.dateadders.DateAdder;
import com.project.app.service.dateadders.DateAdderInstanceHolder;
import com.project.app.service.parsers.DateTimeValuesGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 *
 * @author prafailov
 */
public class DateAdderTest {
    private DateAdder adder;
    private DateTimeValuesGenerator gen;

    @Before
    public void setUp() {
        gen = new DateTimeValuesGenerator();
    }

    @After
    public void tearDown() {
        adder = null;
        gen = null;
    }

    @Test
    public void createAdderTest() {
        Frequency weekly = Frequency.WEEKLY;
        Frequency monthly = Frequency.MONTHLY;

        DateAdder weekAdder = DateAdderInstanceHolder.getDateAdder(weekly);

        DateAdder monthAdder = DateAdderInstanceHolder.getDateAdder(monthly);
        assertNotNull(weekAdder);
        assertNotNull(monthAdder);
    }

    @Test
    public void calcSpanOfDatesTest() {
        Frequency freq = Frequency.QUARTERLY;
        int timeAmount = freq.getOrdinal();
        LocalDateTime startDate = gen.randomDate();
        LocalDateTime endDate = startDate.plusYears(10);

        adder = DateAdderInstanceHolder.getDateAdder(freq);
        LocalDateTime currentDate = startDate;

        while (currentDate.isBefore(endDate)) {
            currentDate = adder.addToDate(currentDate, timeAmount);
        }

        assertEquals(currentDate, endDate);
    }

}
