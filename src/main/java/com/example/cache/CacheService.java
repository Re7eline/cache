package com.example.cache;

public interface CacheService {

    boolean put(String cacheName, String key, Object value);

    Object get(String cacheName, String key);

    void clear(String cacheName);

    void clearAll();

    boolean isCacheExist(String cacheName);
}
