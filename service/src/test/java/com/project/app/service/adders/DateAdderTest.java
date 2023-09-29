package com.project.app.service.adders;

import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.service.dateadders.DateAdder;
import com.project.app.service.dateadders.DateAdderFactory;
import com.project.app.service.parsers.DateTimeValuesGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 *
 * @author prafailov
 */
public class DateAdderTest {
    private DateAdder adder;
    private DateAdderFactory factory;
    private DateTimeValuesGenerator gen;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        factory = new DateAdderFactory();
        gen = new DateTimeValuesGenerator();
    }

    @After
    public void tearDown() {
        factory = null;
        adder = null;
        gen = null;
    }

    @Test
    public void createAdderTest() {
        Frequency weekly = Frequency.WEEKLY;
        Frequency monthly = Frequency.MONTHLY;

        DateAdder weekAdder = factory.getDateAdder(weekly);

        DateAdder monthAdder = factory.getDateAdder(monthly);
        assertNotNull(weekAdder);
        assertNotNull(monthAdder);
    }

    @Test
    public void calcSpanOfDatesTest() {
        Frequency freq = Frequency.QUATERLY;
        int timeAmount = freq.getOrdinal();
        LocalDateTime startDate = gen.randomDate();
        LocalDateTime endDate = startDate.plusYears(10);

        adder = factory.getDateAdder(freq);
        LocalDateTime currentDate = startDate;

        while (currentDate.isBefore(endDate)) {
            currentDate = adder.addToDate(currentDate, timeAmount);
        }

        assertEquals(currentDate, endDate);
    }

}
