package com.spring.restapi.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestapiApplicationTests {
	@Test
	void contextLoads() {
		int a = 10, b = 20;
		int sum = sumOfTwoNum(a, b);

		assertEquals(30, sum);
	}
	public int sumOfTwoNum(int a, int b) {
		return a + b;
	}

}
