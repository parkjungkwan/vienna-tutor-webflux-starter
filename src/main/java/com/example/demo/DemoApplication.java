package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.user.domain.UserModel;
import com.example.demo.user.repository.UserRepository;

import reactor.core.publisher.Mono;

@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@SpringBootApplication
public class DemoApplication {




	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
