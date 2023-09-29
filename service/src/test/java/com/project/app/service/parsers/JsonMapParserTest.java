package com.project.app.service.parsers;

import com.google.gson.JsonElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 *
 * @author prafailov
 */
public class JsonMapParserTest {

    private final static int MAP_SIZE = 20;
    private Map<LocalDateTime, Double> data;
    private JsonMapParser<LocalDateTime, Double> parser;
    private DateTimeValuesGenerator gen;

    @Before
    public void setUp() {
        parser = new JsonMapParser<>();
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
