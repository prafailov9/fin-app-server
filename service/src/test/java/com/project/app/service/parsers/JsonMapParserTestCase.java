package com.project.app.service.parsers;

import com.google.gson.JsonElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.project.app.service.parsers.DateTimeValuesGenerator;
import com.project.app.service.parsers.JsonMapParser;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author prafailov
 */
public class JsonMapParserTestCase {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static int MAP_SIZE = 20;
    private Map<LocalDateTime, Double> data;
    private JsonMapParser<LocalDateTime, Double> parser;
    private DateTimeValuesGenerator gen;

    @Before
    public void setUp() {
        parser = new JsonMapParser();
        gen = new DateTimeValuesGenerator();
        data = gen.generateRandomData(MAP_SIZE);

    }

    @After
    public void tearDown() {
        parser = null;
        gen = null;
        data.clear();
    }

    @Test
    public void parseResultsToJsonTest() {

        String jsonMap = parser.toJsonString(data);
        System.out.println(jsonMap);

        assertNotNull(jsonMap);
    }

    @Test
    public void parseJsonToMapTest() {
        String jsonMap = parser.toJsonString(data);
        Map<LocalDateTime, Double> mappedData = parser.toMap(jsonMap);
        assertNotNull(mappedData);
    }

    @Test
    public void parseToJsonElementTest() {

        JsonElement jo = parser.toJsonElement(data);
        assertNotNull(jo.toString());
    }

}
