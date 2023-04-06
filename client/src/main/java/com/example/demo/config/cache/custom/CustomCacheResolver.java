package com.example.demo.config.cache.custom;

import com.example.demo.config.CacheManagerType;
import com.example.demo.config.cache.CacheProperties;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomCacheResolver implements CacheResolver {
    private final CacheProperties cacheProperties;
    private final RedisCacheManager redisCacheManager;
    private final CaffeineCacheManager caffeineCacheManager;
    private final HazelcastCacheManager hazelCastCacheManager;
    private final SimpleCacheManager simpleCacheManager;
    private final NoOpCacheManager noCacheManager;

    public CustomCacheResolver(CacheProperties cacheProperties,
                               RedisCacheManager redisCacheManager,
                               CaffeineCacheManager caffeineCacheManager,
                               HazelcastCacheManager hazelCastCacheManager,
                               SimpleCacheManager simpleCacheManager,
                               NoOpCacheManager noCacheManager) {
        this.cacheProperties = cacheProperties;
        this.redisCacheManager = redisCacheManager;
        this.caffeineCacheManager = caffeineCacheManager;
        this.hazelCastCacheManager = hazelCastCacheManager;
        this.simpleCacheManager = simpleCacheManager;
        this.noCacheManager = noCacheManager;
    }


    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        final Collection<Cache> caches = new ArrayList<>();
        var cacheableAnnotation = context.getMethod().getAnnotation(Cacheable.class);
        if (cacheableAnnotation != null) {
            var cacheName = cacheableAnnotation.cacheNames()[0];
            var cacheManagerName = context.getArgs()[2].toString();
            var cacheManager = cacheManagerByName(cacheManagerName);
            log.info("Cache {} com cacheManagerName {}", cacheName, cacheManagerName);
            var cache = cacheManager.getCache(cacheName);
            caches.add(cache);
        }
        return caches;
    }

    private CacheManager cacheManagerByName(String cacheManagerName) {
        var type = CacheManagerType.findByName(cacheManagerName);
        return switch (type) {
            case REDIS -> redisCacheManager;
            case CAFFEINE -> caffeineCacheManager;
            case SIMPLE -> simpleCacheManager;
            case HAZEL -> hazelCastCacheManager;
            default -> noCacheManager;
        };
    }

}