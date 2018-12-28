package com.project.app.businesslogic.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * @author prafailov
 * @param <K>
 * @param <V>
 */
public class JsonMapParser<K, V> {

    private final static Gson gson = new GsonBuilder().create();
    private final Type mapType = new TypeToken<Map<K, V>>() {
    }.getType();

    public String toJsonString(Map<K, V> map) {
        String json = gson.toJson(map, mapType);
        return json;
    }

    public JsonElement toJsonElement(Map<K, V> map) {
        JsonElement je = gson.toJsonTree(map, mapType);
        return je;
    }

    public Map<K, V> toMap(String jsonString) {
        Map<K, V> results = gson.fromJson(jsonString, mapType);
        return results;
    }

}
