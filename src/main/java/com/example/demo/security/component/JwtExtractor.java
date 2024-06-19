package com.example.demo.security.component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.demo.security.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExtractor {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire.access}")
    private long accessTokenExpiration;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenExpiration;


    public Authentication getAuthentication(String token) {
        // 토큰 복호화 메소드
        Claims claims = parseClaims(token);
        log.info("claims in JwtProvider  : " + claims);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Object auth = claims.get("auth");
        // [ROLE_USER]
        log.info("auth in JwtProvider : " + auth);

        // 클레임 권한 정보 가져오기
         @SuppressWarnings("unchecked")
        List<String> authorityStrings = (List<String>) claims.get(secretKey);
        // [ROLE_USER]
        log.info("authorityStrings in JwtProvider : " + authorityStrings);

        Collection<? extends GrantedAuthority> authorities =
                authorityStrings.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // [ROLE_USER]
        log.info("authorities in JwtProvider : " + authorities);

        // UserDetails 객체를 만들어서 Authentication 리턴
//        User principal = new User(claims.getSubject(), "", authorities);
//        log.info("principal in JwtProvider : " + principal);
        log.info("claims.getSubject() in JwtProvider : " + claims.getSubject());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                    .verifyWith(new JwtDecoder().getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }

    

    String extractUsername(String jwt){
        return extractClaim(jwt, Claims::getSubject);
    }

    @SuppressWarnings("unchecked")
    List<String> extractRoles(String jwt){
        return extractClaim(jwt, claims -> (List<String>) claims.get("roles"));
    }



    public boolean isTokenValid(String jwt){
        return !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt){
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt){
        try {
            return Jwts.parser()
                    .verifyWith(new JwtDecoder().getSigningKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (JwtException e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }


}
