package com.example.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix="cache.caffeine", name="enabled", havingValue="true")
@EnableCaching
@Slf4j
public class CaffeineConfiguration extends CachingConfigurerSupport {
    @Autowired
    CacheProperties properties;


    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();

        final List<CaffeineCache> caches = new ArrayList<>();
        for (var cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            final String name = cacheNameAndTimeout.getKey().concat("-caffeine");
            log.info("Cache name {} expiration {}", name, cacheNameAndTimeout.getValue());
            caches.add(buildCache(name, cacheNameAndTimeout.getValue()));
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