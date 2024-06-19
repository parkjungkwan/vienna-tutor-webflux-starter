package com.example.demo.security.service.impl;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.demo.security.component.JwtExtractor;
import com.example.demo.security.component.JwtProvider;
import com.example.demo.security.component.JwtValidator;

import org.springframework.util.StringUtils;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class WebFilterImpl implements WebFilter{
    public static final String HEADER_PREFIX = "Bearer ";

    private final JwtValidator tokenValidator;
    private final JwtExtractor tokenExtractor;

    @SuppressWarnings("null")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (StringUtils.hasText(token) && this.tokenValidator.validateToken(token)) {
            return Mono.fromCallable(() -> this.tokenExtractor.getAuthentication(token))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(authentication -> chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
