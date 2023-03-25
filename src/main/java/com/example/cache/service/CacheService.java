package com.example.cache.service;

public interface CacheService {
    void createCache(String cacheName);

    boolean putCache(String cacheName, String key, String value);

    Object getCache(String cacheName, String key);

    void clearCache(String cacheName);

    void clearAll();

    boolean isCacheExist(String cacheName);
}
