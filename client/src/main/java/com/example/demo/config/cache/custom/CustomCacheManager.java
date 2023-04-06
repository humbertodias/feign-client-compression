package com.example.demo.config.cache.custom;

import com.example.demo.config.cache.CacheProperties;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@EnableCaching
public class CustomCacheManager {

    @Bean("customCacheResolver")
    public CacheResolver customCacheResolver(CacheProperties cacheProperties,
                                             RedisCacheManager redisCacheManager,
                                             CaffeineCacheManager caffeineCacheManager,
                                             HazelcastCacheManager hazelCastCacheManager,
                                             SimpleCacheManager simpleCacheManager,
                                             NoOpCacheManager noCacheManager) {
        return new CustomCacheResolver(cacheProperties, redisCacheManager, caffeineCacheManager, hazelCastCacheManager, simpleCacheManager, noCacheManager);
    }

}
