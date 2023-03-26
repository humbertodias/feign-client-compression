package com.example.demo.config;


import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix="cache.ehcache", name="enabled", havingValue="true")
@EnableCaching
public class EhCacheConfiguration extends CachingConfigurerSupport {

    final CacheProperties properties;

    public EhCacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JCacheManagerFactoryBean cacheManagerFactoryBean() throws Exception {
        final JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
        return jCacheManagerFactoryBean;
    }

    @Bean("ehCacheManager")
    @SneakyThrows
    public CacheManager cacheManager() {
        final JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        jCacheCacheManager.setCacheManager(cacheManagerFactoryBean().getObject());
        // TODO - Configure TTL
        // final var cacheNames = properties.getExpirations().keySet().stream().map(c-> c.concat("-ehcache")).collect(Collectors.toList());
        return jCacheCacheManager;
    }

}
