package com.example.demo.user.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.user.domain.UserModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserModel, String> {
  Flux<UserModel> findByLastName(String lastName);
  
  Flux<UserModel> findAll();
  Mono<UserModel> findByUsername(String username);
}