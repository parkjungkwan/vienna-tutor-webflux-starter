package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import com.example.demo.security.component.JwtProvider;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import org.springframework.security.core.userdetails.User;

@EnableReactiveMethodSecurity
@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityFluxConfig {

  private final UserDetailsServiceImpl userDetailsService;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityWebFilterChain securityWebFilterChain(
    ServerHttpSecurity http,
    JwtProvider tokenProvider
    // ReactiveAuthenticationManager reactiveAuthenticationManager
  
  ) throws Exception {
    final String PATH_POSTS = "/posts/**";
    return http
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
            // .authenticationManager(reactiveAuthenticationManager)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/").permitAll() 
                .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .pathMatchers("/api/login").permitAll() 
                .pathMatchers("login").permitAll() 
                .pathMatchers(PATH_POSTS).authenticated()
                        .pathMatchers("/me").authenticated()
                        .pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
            .anyExchange().permitAll()
            )
           // .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)    
        .build();
  }
   private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication,
                                                               AuthorizationContext context) {

        return authentication
                .map(a -> context.getVariables().get("user").equals(a.getName()))
                .map(AuthorizationDecision::new);

    }


  @Bean
  public ReactiveUserDetailsService  userDetailsService(UserRepository userRepository) {


    return username -> userRepository.findByUsername(username)
    .map(u -> User
            .withUsername(u.getUsername()).password(u.getPassword())
            .authorities(u.getRoles().toArray(new String[0]))
            .accountExpired(!u.isActive())
            .credentialsExpired(!u.isActive())
            .disabled(!u.isActive())
            .accountLocked(!u.isActive())
            .build()
    );
  }

   @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }



}
