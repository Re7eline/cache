package com.example.cache.controller;

import com.example.cache.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CacheController {
    private static final Logger logger = LoggerFactory.getLogger("stdout");

    private static final String CALL_ENDPOINT = "call endpoint : %s ";

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/putCache")
    public String putCache(@RequestParam String cacheName,
                           @RequestParam String key,
                           @RequestParam String value) {
        boolean success = cacheService.putCache(cacheName, key, value);
        String result = String.format(CALL_ENDPOINT, "putCache");
        logger.info(result);
        return success ? "Success" : "Failed";
    }

    @GetMapping("/getCache")
    public String getCache(@RequestParam String cacheName, @RequestParam String key) {
        String result = cacheService.getCache(cacheName, key).toString();
        logger.info(String.format(CALL_ENDPOINT, "getCache"));
        return result;
    }

    @DeleteMapping("/clearAll")
    public String clearAll() {
        cacheService.clearAll();
        String result = String.format(CALL_ENDPOINT, "clearAll");
        logger.info(result);
        return result;
    }

    @DeleteMapping("/clearCache/{cacheName}")
    public String clearCache(@PathVariable String cacheName) {
        cacheService.clearCache(cacheName);
        String result = String.format(CALL_ENDPOINT, "clearCache");
        logger.info(result);
        return result;
    }



    @GetMapping("/isCacheExist")
    public String isCacheExist(@RequestParam String cacheName) {
        boolean exists = cacheService.isCacheExist(cacheName);
        String result = String.format(CALL_ENDPOINT, "isCacheExist");
        logger.info(result);
        return exists ? "Exists" : "Not exists";
    }
}
