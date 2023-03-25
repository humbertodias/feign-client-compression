package com.example.demo.config;

import com.example.demo.interceptor.RedisLogInterceptor;
import com.example.demo.util.SerializerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(CacheConfigurationProperties.class)
@EnableCaching
public class RedisConfiguration {

    private RedisCacheConfiguration createCacheConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
    }

    private RedisSerializer<String> keySerializer() {
        return RedisSerializer.string();
    }


    private RedisSerializer<Object> valueSerializer() {
//        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());
        RedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(SerializerUtil.objectMapper());
        return redisSerializer;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(CacheConfigurationProperties properties) {
        log.info("Redis (/lettuce) configuration enabled. With cache timeout {}", properties.getTtl());

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(properties.getRedisHost());
        redisStandaloneConfiguration.setPort(properties.getRedisPort());
        redisStandaloneConfiguration.setPassword(properties.getRedisPassword());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }


    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setEnableTransactionSupport(true);

        var keySerializer = keySerializer();
        var valueSerializer = valueSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);

        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);

        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheConfigurationProperties properties) {
        return createCacheConfiguration(properties.getTtl());
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheConfigurationProperties properties) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (Map.Entry<String, Duration> cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            log.info("Cache key {} expiration {}", cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue());
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }

        var rcm = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration(properties))
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
        rcm.setTransactionAware(true);
        return rcm;
    }

    @Bean
    public CacheOperationSource cacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }

    @Bean
    public CacheInterceptor cacheInterceptor() {
        CacheInterceptor interceptor = new RedisLogInterceptor();
        interceptor.setCacheOperationSources(cacheOperationSource());
        return interceptor;
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new RedisCustomKeyGenerator();
    }
}
