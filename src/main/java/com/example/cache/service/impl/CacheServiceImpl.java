package com.example.cache.service.impl;

import com.example.cache.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger logger = LoggerFactory.getLogger("stdout");
    private Map<String, Map<String, String>> cache;
    private static Map<String, String> cacheData = new HashMap<>();

    @Override
    public void createCache(String cacheName) {
        try {
            cache = new ConcurrentHashMap<>();
            cache.put(cacheName, cacheData);
        } catch (NullPointerException e) {
            logger.info("Cache creation error");
        }
    }

    @Override
    public boolean putCache(String cacheName, String key, String value) {
        if (cacheName == null || key == null || value == null) {
            logger.info("No sufficient data Error");
            return false;
        }
        try {
            if (cache == null) {
                createCache(cacheName);
                cacheData.put(key, value);
                cache.put(cacheName, cacheData);
                logger.info("Cache " + cache + " was successfully created");
                return true;
            } else if (cache.containsKey(cacheName)) {
                cacheData.put(key, value);
                logger.info("Data was successfully added");
            }
        } catch (Exception e) {
            logger.info("Enable to put data into cache");
        }
        return false;
    }

    @Override
    public Object getCache(String cacheName, String key) {
        if (!isCacheExist(cacheName)) {
            String result = "Such cache: ".concat(cacheName + " not exists...");
            logger.info(result);
            return result;
        }
        logger.info("The value of cache" + cacheName + "was founded");
        return cache.get(key);
    }

    @Override
    public void clearCache(String cacheName) {
        if (cacheName == null) {
            logger.info("Cache clearing error");
            return;
        }
        cache.remove(cacheName);
        logger.info("Cache " + cacheName + " successfully cleared");
    }

    @Override
    public void clearAll() {
        if (!cache.isEmpty()) {
            cache.clear();
            logger.info("Cache was successfully cleared!");
        }
    }

    @Override
    public boolean isCacheExist(String cacheName) {
        if (cacheName == null) {
            logger.info("Cache " + cacheName + " not exists!");
            return false;
        } else {
            logger.info("Cache  " + cacheName + "  exists");
        }
        return cache.containsKey(cacheName);
    }

    private boolean isExpired(String cacheName, String key) {
        Map<String, String> cache = this.cache.get(cacheName);
        if (cache == null) {
            logger.info("Cache was expired");
            return true;
        }
        return false;
    }
}