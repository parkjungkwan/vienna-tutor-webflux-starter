package com.example.demo;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test @DisplayName("map vs flapMap")
    void FluxGeneratortTest(){
        String[][] arr = new String[][]{
			{"key1","val1"},{"key2","val2"}
		};

		Arrays.stream(arr)
		.flatMap(i -> Arrays.stream(i))
		.forEach(System.out::println);


		Arrays.stream(arr)
		.map(i -> Arrays.stream(i))
		.forEach(System::println);
    }

}
