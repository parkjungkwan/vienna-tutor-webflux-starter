package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
public class SecurityMvcConfig {


@Bean
SecurityFilterChain springWebFilterChain(HttpSecurity http) throws Exception {
    return http
    .authorizeHttpRequests((authorize) -> authorize
            .anyRequest().permitAll()
    )
    .httpBasic(i -> i.disable())
    .formLogin(i -> i.disable())
    .csrf(i -> i.disable())
    .cors(i -> i.disable())
    .build();
}

}
