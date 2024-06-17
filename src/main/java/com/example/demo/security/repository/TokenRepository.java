package com.example.demo.security.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.security.domain.TokenModel;

import reactor.core.publisher.Mono;
@Repository
public interface TokenRepository extends ReactiveMongoRepository<TokenModel, String> {
    
    Mono<TokenModel> findByEmail(String email);
}
