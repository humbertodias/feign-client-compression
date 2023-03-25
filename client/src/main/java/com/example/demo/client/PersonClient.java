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
    List<PersonDto> getAllRedis(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);
    @GetMapping(value = "faker")
    @Cacheable(value = "person-ehcache", key = "{#amount, #delay, #dataBase}", cacheManager = "ehCacheManager")
    List<PersonDto> getAllEhCache(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String dataBase);

}
