package com.example.est_bootcamp.security;


import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 인증 필터
 * - 모든 요청에 대해 Authorization 헤더에서 JWT 토큰을 확인
 * - 유효한 경우 토큰을 파싱하여 SecurityContext에 Authentication 객체 저장
 * - 이후 Spring Security에서 인증/인가 처리에 활용됨
 */
@Component
public class JwtAuthFilter extends GenericFilter {

    private final JwtTokenProvider provider;

    public JwtAuthFilter(JwtTokenProvider provider) {
        this.provider = provider;
    }

    /**
     * 요청 필터링 로직
     * - 요청 헤더 "Authorization" 값 확인 (Bearer {token} 형식)
     * - JWT 토큰 파싱 및 검증
     * - 성공 시 UsernamePasswordAuthenticationToken 생성 후 SecurityContext에 저장
     * - 실패 시(예외 발생) 아무 처리 없이 필터 체인 계속 진행
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // Authorization 헤더 추출
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                // "Bearer " 접두어 제거 후 JWT 파싱
                var jws = provider.parse(auth.substring(7));
                Claims c = jws.getBody();

                // SecurityContext에 인증 정보 저장
                // 여기서는 subject(보통 username)만 넣고, 권한은 빈 리스트로 처리
                var authToken = new UsernamePasswordAuthenticationToken(
                        c.getSubject(), null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception ignored) {
                // 파싱 실패 또는 만료된 토큰이면 인증 설정하지 않고 무시
            }
        }

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);
    }
}