package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(proxyBeanMethods = false)
@EnableFeignClients
// Spring 3+
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("io.lettuce.core.jfr", "false");
		SpringApplication.run(DemoApplication.class, args);
	}

}
