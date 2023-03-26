package com.example.demo.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "cache.ehcache", name = "enabled", havingValue = "true")
@EnableCaching
public class EhCacheConfiguration extends CachingConfigurerSupport {

    final CacheProperties properties;

    public EhCacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JCacheManagerFactoryBean ehCacheManagerFactoryBean() throws IOException {
        final JCacheManagerFactoryBean cacheManagerFactoryBean = new JCacheManagerFactoryBean();
        cacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
        return cacheManagerFactoryBean;
    }

    @Bean("ehCacheManager")
    public CacheManager ehCacheCacheManager(JCacheManagerFactoryBean bean) {
        return new JCacheCacheManager(bean.getObject());
    }

}
