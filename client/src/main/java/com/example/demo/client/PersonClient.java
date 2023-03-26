package com.example.demo.client;

import com.example.demo.dto.PersonDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "person-client", url = "${person.client.host}", path = "/api")
public interface PersonClient {

    @GetMapping(value = "faker")
    @Cacheable(value = "person-redis", key = "{#amount, #delay, #dataBase}")
    List<PersonDto> getAllPersonRedis(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

    @GetMapping(value = "faker")
    @Cacheable(value = "pet-redis", key = "{#amount, #delay, #dataBase}")
    List<PersonDto> getAllPetRedis(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

    @GetMapping(value = "faker")
    @Cacheable(value = "person-ehcache", key = "{#amount, #delay, #dataBase}", cacheManager = "ehCacheManager")
    List<PersonDto> getAllPersonEhCache(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

    @GetMapping(value = "faker")
    @Cacheable(value = "pet-ehcache", key = "{#amount, #delay, #dataBase}", cacheManager = "ehCacheManager")
    List<PersonDto> getAllPetEhCache(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

    @GetMapping(value = "faker")
    @Cacheable(value = "person-caffeine", key = "{#amount, #delay, #dataBase}", cacheManager = "caffeineCacheManager")
    List<PersonDto> getAllPersonCaffeine(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);
    @GetMapping(value = "faker")
    @Cacheable(value = "pet-caffeine", key = "{#amount, #delay, #dataBase}", cacheManager = "caffeineCacheManager")
    List<PersonDto> getAllPetCaffeine(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

}
