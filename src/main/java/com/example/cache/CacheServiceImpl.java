package com.example.cache;

import com.example.cache.CacheService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheServiceImpl implements CacheService {

    private Map<String, Map<String, Object>> cacheMap = new ConcurrentHashMap<>();

    @Override
    public boolean put(String cacheName, String key, Object value) {
        if (cacheName == null || key == null || value == null) {
            return false;
        }

        Map<String, Object> cache = cacheMap.computeIfAbsent(cacheName, k -> new ConcurrentHashMap<>());
        cache.put(key, value);
        return true;
    }

    @Override
    public Object get(String cacheName, String key) {
        if (cacheName == null || key == null) {
            return null;
        }

        Map<String, Object> cache = cacheMap.get(cacheName);
        if (cache == null) {
            return null;
        }

        return cache.get(key);
    }

    @Override
    public void clear(String cacheName) {
        if (cacheName == null) {
            return;
        }

        cacheMap.remove(cacheName);
    }

    @Override
    public void clearAll() {
        cacheMap.clear();
    }

    @Override
    public boolean isCacheExist(String cacheName) {
        if (cacheName == null) {
            return false;
        }

        return cacheMap.containsKey(cacheName);
    }

    private boolean isExpired(String cacheName, String key) {
        Map<String, Object> cache = cacheMap.get(cacheName);
        if (cache == null) {
            return true;
        }

        Cacheable cacheable = (Cacheable) cache.get(key);
        if (cacheable == null) {
            return true;
        }

        long currentTime = System.currentTimeMillis();
        long timeToLive = cacheable.getTimeToLive();
        long createTime = cacheable.getCreateTime();

        return (currentTime - createTime) >= timeToLive;
    }
}
