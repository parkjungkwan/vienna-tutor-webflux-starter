package com.example.demo.security.service;

import org.springframework.stereotype.Service;

import com.example.demo.security.domain.TokenModel;
import com.example.demo.security.repository.TokenRepository;
import com.example.demo.user.domain.UserModel;
import com.example.demo.user.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

      private final TokenRepository tokenRepository;

      public Mono<TokenModel> saveRefrshToken(String email, String refreshToken, long refreshTokenExpiration){

        TokenModel token = TokenModel.builder()
        .email(email)
        .refreshToken(refreshToken)
        .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiration)))
        .build();


        return tokenRepository.save(token);
        
      }



    
}
