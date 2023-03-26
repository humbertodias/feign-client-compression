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
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix="cache.caffeine", name="enabled", havingValue="true")
@EnableCaching
@Slf4j
public class CaffeineConfiguration extends CachingConfigurerSupport {
    @Autowired
    CacheProperties properties;

    @Bean("caffeineConfig")
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        List<CaffeineCache> caffeineCaches = new ArrayList<>();
        for (Map.Entry<String, Duration> cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            final String key = cacheNameAndTimeout.getKey().concat("-caffeine");
            log.info("Cache key {} expiration {}", key, cacheNameAndTimeout.getValue());
            caffeineCaches.add(buildCache(key, cacheNameAndTimeout.getValue()));
        }
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caffeineCaches);
        return manager;
    }

    private CaffeineCache buildCache(String name, Duration ttl) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl)
                .build());
    }

}