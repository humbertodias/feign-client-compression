package com.example.demo.config.cache;


import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class SimpleCacheConfiguration {

    final CacheProperties properties;

    public SimpleCacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }


    @Bean("simpleCacheManager")
    public SimpleCacheManager simpleCacheCacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();

        final List<CaffeineCache> caches = new ArrayList<>();
        for (var cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            log.info("Cache name {} expiration {}", cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue());
            caches.add(buildCache(cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue()));
        }

        cacheManager.setCaches(caches);
        return cacheManager;
    }

    private CaffeineCache buildCache(String name, Duration ttl) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl)
                .build());
    }


}
