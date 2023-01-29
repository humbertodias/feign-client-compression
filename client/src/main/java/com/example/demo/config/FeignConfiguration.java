package com.example.demo.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Client getClient() {
        return new feign.httpclient.ApacheHttpClient();// use the ApacheHttp client
    }

}