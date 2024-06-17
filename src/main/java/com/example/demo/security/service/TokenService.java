package com.example.demo.security.service;

import org.springframework.stereotype.Service;

import com.example.demo.security.domain.TokenModel;
import com.example.demo.security.repository.TokenRepository;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

      private final TokenRepository tokenRepository;

      public TokenModel createRefrshToken(String email){

        TokenModel token = TokenModel.builder()
        .email(email)
        .refreshToken(UUID.randomUUID().toString())
        .expiryDate(Instant.now().plusMillis(36000))
        .build();


        return token;
        
      }
    
}
