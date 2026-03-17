package com.esmt.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.esmt.cache.CacheNames;
import com.github.benmanes.caffeine.cache.Caffeine;
 

@Configuration
public class CacheConfig {

    private Caffeine<Object, Object> caffeine(long duration, TimeUnit unit, int maxSize) {
        return Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(maxSize)
                .expireAfterWrite(duration, unit)
                .recordStats();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();

        List<Cache> caches = new ArrayList<>();





        // ---------------------
        //  FISH PRICES
        // ---------------------
        caches.add(new CaffeineCache(CacheNames.FISH_PRICES_ALL,
                caffeine(24, TimeUnit.HOURS, 2000).build()));

        // ---------------------
        //  DOMAIN DATA
        // ---------------------
        caches.add(new CaffeineCache(CacheNames.DOMAIN_DATA_BY_NAME,
                caffeine(24, TimeUnit.HOURS, 1000).build()));

        // ---------------------
        //  DOMAIN LOOKUPS (24 HOURS)
        // ---------------------
        caches.add(new CaffeineCache(CacheNames.COUNTRIES,
                caffeine(24, TimeUnit.HOURS, 1000).build()));
        caches.add(new CaffeineCache(CacheNames.CURRENCIES,
                caffeine(24, TimeUnit.HOURS, 1000).build()));
        caches.add(new CaffeineCache(CacheNames.ROLES,
                caffeine(24, TimeUnit.HOURS, 1000).build()));

        manager.setCaches(caches);
        return manager;
    }
}
