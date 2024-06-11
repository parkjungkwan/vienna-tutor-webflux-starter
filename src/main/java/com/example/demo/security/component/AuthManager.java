package com.example.demo.security.component;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.demo.security.filter.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

@Log
@Component
@RequiredArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {



     private final TokenProvider tokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // return Mono.just(authentication)
        //         .cast(JwtToken.class)
        //         .filter(jwtToken -> tokenProvider.isTokenValid(jwtToken.getToken()))
        //         .map(jwtToken -> jwtToken.withAuthenticated(true))
        //         .switchIfEmpty(Mono.error(new JwtAuthenticationException("Invalid token.")));
        return Mono.empty();
    }

}
