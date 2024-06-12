package com.example.demo.common.domain;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Messenger {
    private String message;
    private int status;
    private String accessToken;
    private String refreshToken;
    private Long id;
    
}