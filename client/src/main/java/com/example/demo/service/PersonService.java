package com.example.demo.service;


import com.example.demo.client.PersonClient;
import com.example.demo.dto.PersonDto;
import com.example.demo.util.SerializerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class PersonService {

    static ObjectMapper objectMapper = SerializerUtil.objectMapper();
    final
    PersonClient personClient;
    //    @Qualifier("caffeineCacheManager")
    @Autowired
    CacheManager cacheManager;

    @Value("${person.client.host}")
    String personClientUrl;

    public PersonService(PersonClient personClient) {
        this.personClient = personClient;
    }

    private static final String PATTERN_FORMAT = "dd.MM.yyyy";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    public List<PersonDto> getAll(int amount, int delay) {
        return personClient.getAllPersonRedis(amount, delay, dataBase());
    }

    private String dataBase() {
        return formatter.format(Instant.now());
    }

    public CompletableFuture<List<PersonDto>> getAllAsync(int amount, int delay) {
        return CompletableFuture.completedFuture(personClient.getAllEhCache(amount, delay, dataBase()));
    }

    public List<PersonDto> getAllPersonRedis(int amount, int delay) {
        return personClient.getAllPersonRedis(amount, delay, dataBase());
    }

    public List<PersonDto> getAllPetRedis(int amount, int delay) {
        return personClient.getAllPetRedis(amount, delay, dataBase());
    }

    public List<PersonDto> getAllPersonCaffeine(int amount, int delay) {
        return personClient.getAllPersonCaffeine(amount, delay, dataBase());
    }

    public List<PersonDto> getAllPetCaffeine(int amount, int delay) {
        return personClient.getAllPetCaffeine(amount, delay, dataBase());
    }

    public List<PersonDto> getAllEhCache(int amount, int delay) {
        return personClient.getAllEhCache(amount, delay, dataBase());
    }

    public String getPersonClientUrl() {
        return personClientUrl;
    }

    @SneakyThrows
    public Map<Object, Map> getCaches() {
        final Map<Object, Map> caches = new HashMap<>();
        cacheManager.getCacheNames()
                .forEach(name -> {
                    org.springframework.cache.Cache cache = cacheManager.getCache(name);
                    caches.put(name, nativeCacheAsMap(cache));
                });
        return caches;
    }

    private Map<Object, Object> nativeCacheAsMap(org.springframework.cache.Cache cache) {
        if (cache instanceof CaffeineCache) {
            CaffeineCache caffeineCache = (CaffeineCache) cache;
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            return nativeCache.asMap();
        }
        return new HashMap<>();
    }

    public void cleanCache() {
        cacheManager.getCacheNames().forEach(name -> {
            log.info("Cleaning {} cache", name);
            cacheManager.getCache(name).clear();
        });
    }
}
