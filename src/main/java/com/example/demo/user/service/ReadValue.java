package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadValue {

    @Value("${jwt.expire.access}")
    private long accessTokenExpire;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenExpire;

    public ReadValue() {
    }

    public ReadValue(@Value("${jwt.expire.access}") String accessExpire,
            @Value("${jwt.expire.refresh}") String refreshExpire) {

        // test 값을 확인하기 위한 출력
        System.out.println("ReadValue accessExpire : " + accessExpire);
        System.out.println("ReadValue refreshExpire: " + refreshExpire);
    }

    public long getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public long getRefreshTokenExpire() {
        return refreshTokenExpire;

    }
}