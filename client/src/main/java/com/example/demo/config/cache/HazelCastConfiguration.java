package com.example.demo.config.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Slf4j
public class HazelCastConfiguration  {

    final CacheProperties properties;

    public HazelCastConfiguration(CacheProperties properties) {
        this.properties = properties;
    }


    @Bean
    public ClientConfig clientConfig() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(properties.getHazelCastAddress());
        clientConfig.setClusterName(properties.getHazelCastClusterName());
        addCacheConfigWithTTL(clientConfig);
        return clientConfig;
    }


    private void addCacheConfigWithTTL(ClientConfig clientConfig){
        for (Map.Entry<String, Duration> cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            log.info("Cache key {} expiration {}", cacheNameAndTimeout.getKey(), cacheNameAndTimeout.getValue());
            clientConfig.addNearCacheConfig(
                    buildCacheConfig(cacheNameAndTimeout.getKey(),
                            cacheNameAndTimeout.getValue())
            );
        }
    }

    private NearCacheConfig buildCacheConfig(String name, Duration ttl) {
        NearCacheConfig nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setName(name);
        nearCacheConfig.setTimeToLiveSeconds((int)ttl.toSeconds());
        return nearCacheConfig;
    }

    @Bean("hazelCastCacheManager")
    public HazelcastCacheManager cacheManager(ClientConfig clientConfig) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        return new HazelcastCacheManager(hazelcastInstance);
    }



}
