package com.project.app.cache;

public class CacheObject<K, V> {
    private final V value;
    private final K key;

    private CacheObject(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> CacheObject<K, V> of(final K key, final V value) {
        return new CacheObject<>(key, value);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }


}
