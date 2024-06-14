package com.example.demo.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.domain.Messenger;
import com.example.demo.user.domain.UserDTO;
import com.example.demo.user.domain.UserModel;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


  private final UserRepository userRepository;

  public Flux<UserModel> getAllUsers() {
    return userRepository.findAll();
  }

  public Mono<UserModel> getUserDetailByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Mono<UserModel> getUserDetailById(String id) {
    return userRepository.findById(id);
  }

  public Mono<Messenger> addUser(UserModel user) {
    return userRepository.save(user).flatMap(i -> Mono.just(Messenger.builder().message("SUCCESS").build()))
    .switchIfEmpty(Mono.just(Messenger.builder().message("FAILURE").build()))
    ;
  }

  public Mono<UserModel> updateUser(String id, UserModel user) {
    return userRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
        .flatMap(optionalUser -> {
          if (optionalUser.isPresent()) {
            user.setUserId(id);
            return userRepository.save(user);
          }

          return Mono.empty();
        });
  }

  public Mono<Void> deleteUser(String id) {
    return userRepository.deleteById(id);
  }

  public Mono<Void> deleteAllUsers() {
    return userRepository.deleteAll();
  }

  public Flux<UserModel> findByLastName(String lastName) {
    return userRepository.findByLastName(lastName);
  }

  public Mono<Messenger> login(UserModel user) {
    log.info("로그인에 사용되는 이메일 : {}",user.getEmail());
    // Sync
    return userRepository.findByEmail(user.getEmail())
    .filter(i -> i.getPassword().equals(user.getPassword()))
    .map(i -> UserDTO.builder().email(i.getEmail()).firstName(i.getFirstName()).lastName(i.getLastName()).build())
    .log()
    .map(i -> Messenger.builder().message("SUCCESS").data(i)
    .accessToken("fake-access-token")
    .refreshToken("fake-refresh-token")
    .build())
    
    ;
  }
  public Mono<Messenger> login2(UserModel user) {
    log.info("로그인 2 에 사용되는 이메일 : {}",user.getEmail());
    // Async
    // attach 방식으로 사용
    return userRepository.findByEmail(user.getEmail())
    .filter(i -> i.getPassword().equals(user.getEmail()))
    .flatMap(i -> Mono.just(UserDTO.builder().email(i.getEmail()).firstName(i.getFirstName()).lastName(i.getLastName()).build()))
    .log()
    .flatMap(i -> Mono.just(Messenger.builder().data(i).build()))
    
    ;
  }
}