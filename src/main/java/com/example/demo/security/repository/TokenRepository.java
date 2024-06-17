package com.example.demo.security.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.security.domain.TokenModel;

import reactor.core.publisher.Mono;

public interface TokenRepository extends ReactiveMongoRepository<TokenModel, String> {
    
    Mono<TokenModel> findByEmail(String email);
}
