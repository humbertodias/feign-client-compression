package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class FakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakerApplication.class, args);
    }

}
