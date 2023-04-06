package com.example.demo.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class CaffeineConfiguration {

    final CacheProperties properties;

    public CaffeineConfiguration(CacheProperties properties) {
        this.properties = properties;
    }


    @Bean("caffeineCacheManager")
    public CaffeineCacheManager caffeineCacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        for (var cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            log.info("Cache name {} expiration {}", cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue());
            cacheManager.registerCustomCache(
                    cacheNameAndTimeout.getKey(),
                    Caffeine.newBuilder()
                            .expireAfterWrite(cacheNameAndTimeout.getValue())
                            .build()
            );

        }
        return cacheManager;
    }

}