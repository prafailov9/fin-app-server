package com.project.app.service.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author prafailov
 */
public class DateTimeValuesGenerator {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ThreadLocalRandom rand = ThreadLocalRandom.current();

    public Map<LocalDateTime, Double> generateRandomData(int mapSize) {
        Map<LocalDateTime, Double> contents = new HashMap<>();

        while (contents.size() < mapSize) {
            LocalDateTime date = randomDate();
            Double value = randomDoubleValue(1, 50);
            contents.put(date, value);
        }

        return contents;
    }

    public Double randomDoubleValue(final int origin, final int bound) {
        return rand.nextDouble(origin, bound);
    }

    public LocalDateTime randomDate() {
        int year = rand.nextInt(2000, 2020 + 1);
        String month = Integer.toString(rand.nextInt(1, 11 + 1));
        String day = Integer.toString(rand.nextInt(1, 29 + 1));

        String hours = Integer.toString(rand.nextInt(0, 23 + 1));
        String minutes = Integer.toString(rand.nextInt(0, 59 + 1));
        String seconds = Integer.toString(rand.nextInt(0, 59 + 1));

        month = addZero(month);
        day = addZero(day);
        hours = addZero(hours);
        minutes = addZero(minutes);
        seconds = addZero(seconds);

        String strDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
        LocalDateTime date = LocalDateTime.parse(strDate, DTF);
        return date;
    }

    private String addZero(String str) {
        if (str.length() < 2) {
            str = "0" + str;
        }
        return str;
    }

}
