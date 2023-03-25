package com.example.demo.interceptor;

import com.example.demo.util.SerializerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheInterceptor;

import java.util.Objects;

@Slf4j
public class RedisLogInterceptor extends CacheInterceptor {

    private final ObjectMapper objectMapper = SerializerUtil.objectMapper();

    @Override
    protected Cache.ValueWrapper doGet(Cache cache, Object key) {
        doLog(cache, key);
        return super.doGet(cache, key);
    }

    private void doLog(Cache cache, Object key) {
        final Object value = cache.get(key);
        final String cacheKey = key(key);
        final String cacheValue = value(value);
        log.info("Cache name {} key {} value {}", cache.getName(), cacheKey, cacheValue);
    }

    @SneakyThrows
    private String key(Object key) {
        return Objects.toString(key);
    }

    @SneakyThrows
    private String value(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}