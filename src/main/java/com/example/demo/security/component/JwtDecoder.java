package com.example.demo.security.component;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtDecoder {

    @Value("${jwt.secret}")
    private String secret;

    public SecretKey getSigningKey() {
        log.info("시크릿키값 : {}", "adfadfadfadfadfafefadfadfdf12121");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
