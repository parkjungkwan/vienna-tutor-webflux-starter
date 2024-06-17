package com.example.demo;

import java.util.Arrays;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test @DisplayName("Java: map vs flapMap")
    void JavaMapFlpMapTest(){
        String[][] arr = new String[][]{
			{"key1","val1"},{"key2","val2"}
		};

		Arrays.stream(arr)
		.flatMap(i -> Arrays.stream(i))
		.forEach(System.out::println);


		Arrays.stream(arr)
		.map(i -> Arrays.stream(i))
		.forEach(j -> j.forEach(System.out::println));
    }


	@Test @DisplayName("Java: new Object")
    void JavaObjectTest(){
        String name = "Tom";
		String capitalName = name.toUpperCase();
		String greeting = "Hello " + capitalName + "!";
		System.out.println(greeting);
    }
	@Test @DisplayName("WebFlux: new Object")
    void WebFluxNewObjectTest(){
       Mono.just("Tom")
	   .map(String::toUpperCase)
	   .map(j -> "Hello " + j + "!")
	   .subscribe(System.out::println);
    }

	@Test @DisplayName("WebFlux: Map")
    void WebFluxFlatMapTest(){
       Flux.just("bangez",".","com")
	   .map(s-> Flux.just(s.toUpperCase().split("")))
	   .subscribe(System.out::println);
    }
	@Test @DisplayName("WebFlux: FlatMap")
    void WebFluxMapTest(){
       Flux.just("bangez",".","com")
	   .flatMap(s-> Flux.just(s.toUpperCase().split("")))
	   .subscribe(System.out::println);
    }

	

	


}
