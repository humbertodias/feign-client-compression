package com.example.demo.interceptor;

import com.example.demo.util.SerializerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheInterceptor;

import java.util.Objects;

@Slf4j
public class CacheLogInterceptor extends CacheInterceptor {

    private final ObjectMapper objectMapper = SerializerUtil.objectMapper();

    @Override
    protected Cache.ValueWrapper doGet(Cache cache, Object key) {
        doLog("doGet", cache, key, null);
        return super.doGet(cache, key);
    }

    @Override
    protected void doPut(Cache cache, Object key, Object result) {
        doLog("doPut", cache, key, result);
        super.doPut(cache, key, result);
    }

    private void doLog(String method, Cache cache, Object key, Object result) {
        final Object value = cache.get(key);
        final String cacheKey = key(key);
        final String cacheValue = value(value);
        log.info("Cache.{} name {} key {} value {} result {}", method, cache.getName(), cacheKey, cacheValue, result);
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