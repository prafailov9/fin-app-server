package com.project.app.service.adapters;

import com.project.app.service.parsers.JsonMapParser;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author prafailov
 */
public class JsonMapAdapter extends XmlAdapter<String, Map<LocalDateTime, Double>> {

    private final JsonMapParser<LocalDateTime, Double> parser = new JsonMapParser<>();

    @Override
    public Map<LocalDateTime, Double> unmarshal(String stringMap) throws Exception {
        return parser.toMap(stringMap);

    }

    @Override
    public String marshal(Map<LocalDateTime, Double> resultsMap) throws Exception {
        return parser.toJsonString(resultsMap);
    }

}
