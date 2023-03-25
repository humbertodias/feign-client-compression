package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@Disabled
@SpringBootTest(classes = FakerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FakerApplicationTests {

	@Test
	void contextLoads() {
	}

}
