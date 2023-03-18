package com.example.cache;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/{cacheName}")
    public ResponseEntity<?> createCache(@PathVariable String cacheName) {
        boolean cacheExists = cacheService.isCacheExist(cacheName);
        if (!cacheExists) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Cache already exists");
        }
    }

    @PostMapping("/{cacheName}/{key}")
    public ResponseEntity<?> put(@PathVariable String cacheName, @PathVariable String key, @RequestBody Object object) {
        boolean objectPut = cacheService.put(cacheName, key, object);
        if (objectPut) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Cache doesn't exist");
        }
    }

    @GetMapping("/{cacheName}/{key}")
    public ResponseEntity<?> get(@PathVariable String cacheName, @PathVariable String key) {
        Object object = cacheService.get(cacheName, key);
        if (object != null) {
            return ResponseEntity.ok(object);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> clearAll() {
        cacheService.clearAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cacheName}")
    public ResponseEntity<?> clear(@PathVariable String cacheName) {
        cacheService.clear(cacheName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cacheName}")
    public ResponseEntity<?> isCacheExist(@PathVariable String cacheName) {
        boolean cacheExists = cacheService.isCacheExist(cacheName);
        if (cacheExists) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
