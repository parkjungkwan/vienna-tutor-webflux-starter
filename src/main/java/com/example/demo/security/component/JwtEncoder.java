package com.example.demo.security.component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.io.Encoders;

public class JwtEncoder {
    

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
