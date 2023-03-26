package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "cache")
@Data
public class CacheProperties {

    private Duration ttl = Duration.ofSeconds(60);
    private int redisPort = 6379;
    private String redisHost = "localhost";
    private String redisPassword = "password";

    // Mapping of cacheNames to expira-after-write timeout in seconds
    private Map<String, Duration> expirations = new HashMap<>();
}
