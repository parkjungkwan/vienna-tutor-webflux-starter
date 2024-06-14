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
    private Object data; // 제네릭 객체
    private String accessToken;
    private String refreshToken;
    
}