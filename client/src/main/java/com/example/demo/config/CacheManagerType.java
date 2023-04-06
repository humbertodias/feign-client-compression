package com.example.demo.config;

import java.util.Arrays;

public enum CacheManagerType {
    REDIS("redisCacheManager"),
    CAFFEINE("caffeineCacheManager"),
    SIMPLE("simpleCacheManager"),
    HAZEL("hazelCastCacheManager"),
    NO("noCacheManager");

    private final String name;

    CacheManagerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CacheManagerType findByName(String name){
        return Arrays.stream(values()).filter(e-> e.name.equals(name)).findFirst().orElse(NO);
    }
}
