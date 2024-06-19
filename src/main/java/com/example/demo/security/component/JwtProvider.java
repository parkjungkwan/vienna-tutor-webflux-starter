package com.example.demo.security.component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.security.domain.TokenDTO;
import com.example.demo.security.exception.JwtAuthenticationException;
import com.example.demo.user.domain.UserModel;
import com.example.demo.user.service.ReadValue;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire.access}")
    private long accessTokenExpiration;

    @Value("${jwt.expire.refresh}")
    private long refreshTokenExpiration;




    public String  generateToken(Authentication authentication, 
                                    List<GrantedAuthority> authorities,
                                    String isAccessToken) {
        log.info("JwtProvider에서 주입받은 사용자 정보 : " + authentication);
        
        /**
         * JwtProvider에서 주입받은 사용자 정보 : TestingAuthenticationToken
         *  [Principal=tom@test.com, Credentials=[PROTECTED], Authenticated=true, 
         * Details=null, Granted Authorities=[]]
         * 
        */
        log.info("JwtProvider에서 주입받은 권한 정보 : " + authorities);
        
        
        Map<String, Object> claims = new HashMap<>();
        // claims.put("authorities",
        // authorities.stream().map(GrantedAuthority::getAuthority)
        // .collect(Collectors.toList()));
        List<String> roles = new ArrayList<>();
        roles.add("user");
        claims.put("authorities",roles);
        ReadValue readValue = new ReadValue();
        long accessTokenExpire = readValue.getAccessTokenExpire();
        long refreshTokenExpire = readValue.getRefreshTokenExpire();

        log.info("JwtProvider에서 주입받은 클레임 권한 정보 : " + claims);
        log.info("JwtProvider에서 주입받은 사용자 아이디 : " + authentication.getName());

        if(accessTokenExpire==0L){ accessTokenExpire=600000L;}
        if(refreshTokenExpire==0L){ refreshTokenExpire=600000L;}

        log.info("로그인 성공시 접속토큰 유효기간  : {}", accessTokenExpire);
        log.info("로그인 성공시 재생토큰 유효기간  : {}", refreshTokenExpire);

        Key key = getKeyFromBase64EncodedKey("afdadasdfa12323231231231232312787fghgfhffdfd");

        return Jwts.builder()
        .claims(claims)
        .subject(authentication.getName())
        //.claim("roles", List.of("user"))
        .expiration(Date.from(Instant.now().plusSeconds(isAccessToken.equals("accessToken") ? refreshTokenExpiration : accessTokenExpiration)))
        // .signWith(new JwtDecoder().getSigningKey(), Jwts.SIG.HS256)
        .signWith(key)
        .compact();



    }

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
}

private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

    return Keys.hmacShaKeyFor(keyBytes);
}

    

}