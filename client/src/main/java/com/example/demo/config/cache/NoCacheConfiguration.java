package com.example.demo.config.cache;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class NoCacheConfiguration  {

    final CacheProperties properties;

    public NoCacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean("noCacheManager")
    public NoOpCacheManager cacheManager() {
        return new NoOpCacheManager();
    }

}
