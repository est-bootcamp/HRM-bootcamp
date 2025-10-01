package com.example.est_bootcamp.api;

import com.example.est_bootcamp.security.CustomUserDetails;
import com.example.est_bootcamp.security.JwtTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager; // 스프링 시큐리티 인증 매니저
    private final JwtTokenProvider provider; // JWT 토큰 발급/검증 유틸

    /**
     * 로그인 API (JWT 발급)
     * - 요청: POST /api/auth/login
     * - Body(JSON): { "username": "아이디", "password": "비밀번호" }
     * - 응답: { "accessToken": "JWT토큰값" }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        try {
            // 1. 스프링 시큐리티 인증 실행 (아이디/비밀번호 검증)
            var authToken = new UsernamePasswordAuthenticationToken(req.username, req.password);
            var auth = authenticationManager.authenticate(authToken);

            // 2. principal(CustomUserDetails) 꺼내기
            var userDetails = (CustomUserDetails) auth.getPrincipal();

            // 3. JWT 발급 (role 포함)
            String role = (userDetails.getUserAccount().getUsRole() != null)
                    ? userDetails.getUserAccount().getUsRole().toUpperCase()
                    : "USER";

            String token = provider.createToken(
                    userDetails.getUsername(), // subject (로그인 ID)
                    Map.of("role", role)   // payload(claims) - 역할(role) 추가
            );

            // 4. 성공 응답 (액세스 토큰 반환)
            return ResponseEntity.ok(Map.of("accessToken", token));

        } catch (BadCredentialsException e) {
            // 아이디 또는 비밀번호 불일치
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "아이디 또는 비밀번호가 올바르지 않습니다."));
        } catch (Exception e) {
            // 그 외 서버 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류가 발생했습니다. 관리자에게 문의하세요."));
        }
    }

    /**
     * 로그인 요청 DTO
     * - 클라이언트에서 JSON 형식으로 전달되는 로그인 정보
     * - username: 로그인 ID 또는 이메일
     * - password: 평문 비밀번호
     */
    @Data
    public static class LoginReq {
        private String username;  // loginId or email
        private String password;
    }
}

