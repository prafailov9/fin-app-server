package com.project.app.businesslogic.adapters;

import com.project.app.businesslogic.parsers.JsonMapParser;
import java.time.LocalDateTime;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author prafailov
 */
public class JsonMapAdapter extends XmlAdapter<String, Map<LocalDateTime, Double>> {

    private final JsonMapParser<LocalDateTime, Double> parser = new JsonMapParser<>();

    @Override
    public Map<LocalDateTime, Double> unmarshal(String stringMap) throws Exception {
        Map<LocalDateTime, Double> resultsMap = parser.toMap(stringMap);
        return resultsMap;

    }

    @Override
    public String marshal(Map<LocalDateTime, Double> resultsMap) throws Exception {
        String stringMap = parser.toJsonString(resultsMap);
        return stringMap;
    }

}
