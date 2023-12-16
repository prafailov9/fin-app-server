package com.project.app.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;

    public LRUCache(final int capacity) {
        this.capacity = capacity;

        // The oldest entry will be removed automatically when capacity is exceeded.
        // accessOrder = true -> order of the cache will be
        // affected by get() calls which maintain LRU property.
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    @Override
    public synchronized void put(K key, V value) {
        if (key == null || value == null) {
            throw  new IllegalArgumentException("key or value cannot be null!");
        }
        cache.put(key, value);
    }

    @Override
    public synchronized Optional<V> get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return Optional.ofNullable(cache.get(key));
    }
}
