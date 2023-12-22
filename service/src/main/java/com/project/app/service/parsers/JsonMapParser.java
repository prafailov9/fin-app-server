package com.project.app.service.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @param <K>
 * @param <V>
 * @author prafailov
 */
public class JsonMapParser<K, V> {

    private final static Gson gson = new GsonBuilder().create();
    private final Type mapType = new TypeToken<Map<K, V>>() {
    }.getType();

    public String toJsonString(Map<K, V> map) {
        return gson.toJson(map, mapType);
    }

    public JsonElement toJsonElement(Map<K, V> map) {
        return gson.toJsonTree(map, mapType);
    }

    public Map<K, V> toMap(String jsonString) {
        return gson.fromJson(jsonString, mapType);
    }

}
