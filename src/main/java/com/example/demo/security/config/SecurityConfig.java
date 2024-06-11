package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        // 개발자가 기획에 따라 커스터마이징 해야 함
//     private final CustomRequestFilter jwtAuthenticationFilter ;
//     private final UserDetailsService customerUserDetailsService ;



@Bean
SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
    return http
    // Demonstrate that method security works
    // Best practice to use both for defense in depth
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
/**

SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                     ReactiveAuthenticationManager authenticationManager,
                                                     ServerAuthenticationConverter authenticationConverter) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        log.info("------------------- 스프링 시큐리티 설정파일 진입함 -------------------  ");

        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, "/api/login").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                // .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .build();
    }

 */