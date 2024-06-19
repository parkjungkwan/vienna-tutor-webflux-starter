package com.example.demo.user.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.common.domain.Messenger;
import com.example.demo.security.component.JwtProvider;
import com.example.demo.security.repository.TokenRepository;
import com.example.demo.security.service.TokenService;
import com.example.demo.user.domain.UserDTO;
import com.example.demo.user.domain.UserModel;
import com.example.demo.user.exception.DuplicateException;
import com.example.demo.user.repository.UserRepository;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.expire.access}")
  private long accessTokenExpire;

  @Value("${jwt.expire.refresh}")
  private long refreshTokenExpire;

  public Flux<UserModel> getAllUsers() {
    return userRepository.findAll();
  }

  public Mono<UserModel> getUserDetailByEmail(String email) {
    return userRepository.findByUsername(email);
  }

  public Mono<UserModel> getUserDetailById(String id) {
    return userRepository.findById(id);
  }

  public Mono<Messenger> addUser(UserModel reqeustUser) {
    String email = reqeustUser.getUsername();
    Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByUsername(email).block());
    if (existingUser.isPresent()) {
      throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
    }

    String hashedPassword = passwordEncoder.encode(reqeustUser.getPassword());
    UserModel responseUser = UserModel.builder()
        .username(reqeustUser.getUsername())
        .password(hashedPassword)
        .firstName(reqeustUser.getFirstName())
        .lastName(reqeustUser.getLastName())
        .build();
    return userRepository.save(responseUser).flatMap(i -> Mono.just(Messenger.builder().message("SUCCESS").build()))
        .switchIfEmpty(Mono.just(Messenger.builder().message("FAILURE").build()));
  }

  public Mono<UserModel> updateUser(String id, UserModel user) {
    return userRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
        .flatMap(optionalUser -> {
          if (optionalUser.isPresent()) {
            user.setId(id);
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

  public Mono<Messenger> login(UserModel reqeustUser) {
    log.info("로그인에 사용되는 이메일 : {}", reqeustUser.getUsername());
    // SecurityContext 조회
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    // 인증된 정보 조회
    Authentication authentication = new TestingAuthenticationToken(reqeustUser.getUsername(),
        reqeustUser.getPassword(),
        new ArrayList<GrantedAuthority>());
    // securityContext.setAuthentication(authentication);

    String tempName = authentication.getName();

    log.info(" authentication 내부의 이름 : {}", tempName);

    var accessToken = jwtProvider.generateToken(authentication,
        new ArrayList<GrantedAuthority>(),
        "accessToken");
    if (accessToken.equals("")) {
      log.info("접속토큰 발급 실패");
    }

    var refreshToken = jwtProvider.generateToken(authentication,
        new ArrayList<GrantedAuthority>(),
        "refreshToken");
    if (refreshToken.equals("")) {
      log.info("리프레시 토큰 발급 실패");
    }
    ReadValue readValue = new ReadValue();
    long accessTokenExpire = readValue.getAccessTokenExpire();
    long refreshTokenExpire = readValue.getRefreshTokenExpire();

    log.info("로그인 성공시 접속토큰  : {}", accessToken);
    log.info("로그인 성공시 재생토큰  : {}", refreshToken);


    tokenService.saveRefrshToken(reqeustUser.getUsername(), refreshToken, refreshTokenExpire);

    log.info("로그인 2 에 사용되는 이메일 : {}", reqeustUser.getUsername());
    // Async
    // attach 방식으로 사용
    return userRepository.findByUsername(reqeustUser.getUsername())
        .filter(i -> i.getPassword().equals(reqeustUser.getPassword()))
        .flatMap(i -> Mono
            .just(UserDTO.builder().username(i.getUsername()).firstName(i.getFirstName()).lastName(i.getLastName())
                .build()))
        .log()
        .flatMap(i -> Mono.just(Messenger.builder().data(i).build()))

    ;
  }

}