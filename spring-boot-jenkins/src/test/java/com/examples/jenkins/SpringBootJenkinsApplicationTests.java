package com.examples.jenkins;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBootJenkinsApplicationTests {
	
	@Autowired
	ApplicationContext context;

	@Test
	void contextLoads() {
	}
	
//	@Test
//	void testContext() {
//		assertNotNull(context);
//	}
}
