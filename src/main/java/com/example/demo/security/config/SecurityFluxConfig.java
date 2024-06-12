package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebFluxSecurity
public class SecurityFluxConfig {


@Bean
SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
    return http
    .authorizeExchange((authorize) -> authorize
            .anyExchange().permitAll()
    )
    .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
    .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
    .csrf(ServerHttpSecurity.CsrfSpec::disable)
    .cors(ServerHttpSecurity.CorsSpec::disable)
    .build();
}
    


}
