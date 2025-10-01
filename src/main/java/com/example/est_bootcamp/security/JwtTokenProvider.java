package com.example.est_bootcamp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT 토큰 생성 및 검증을 담당하는 Provider 클래스
 * - 토큰 생성 시 subject(보통 username)과 claims(부가 정보, 예: roles 등)를 포함
 * - 토큰 파싱 시 유효성 검증 및 클레임 추출
 */
@Component
public class JwtTokenProvider {
    private final Key key;              // 서명에 사용되는 비밀 키
    private final long validityMillis;  // 토큰 유효 시간(ms 단위)

    /**
     * 생성자
     */
    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-validity-seconds}") long validitySeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes()); // 시크릿 키를 HMAC-SHA256 키로 변환
        this.validityMillis = validitySeconds * 1000;     // 초 단위를 밀리초로 변환
    }

    /**
     * JWT 토큰 생성
     */
    public String createToken(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)                       // 토큰 주체 (username 등)
                .addClaims(claims)                         // 사용자 권한/부가 정보
                .setIssuedAt(new Date(now))                // 발급 시간
                .setExpiration(new Date(now + validityMillis)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)   // HMAC-SHA256으로 서명
                .compact();
    }

    /**
     * JWT 토큰 파싱 및 검증
     */
    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)     // 서명 검증용 키 설정
                .build()
                .parseClaimsJws(token); // 서명 검증 및 payload 파싱
    }
}
