package com.example.demo.client;

import com.example.demo.dto.PersonDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "person-client", url = "${person.client.host}", path = "/api")
@CacheConfig(cacheResolver = "customCacheResolver")
public interface PersonClient {

    @GetMapping(value = "faker")
    @Cacheable(cacheNames = "person", key = "{#amount, #delay, #cacheManager}")
    List<PersonDto> getPerson(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay, String cacheManager);

}
