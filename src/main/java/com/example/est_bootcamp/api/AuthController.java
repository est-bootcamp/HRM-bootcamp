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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        try {
            // 1. 스프링 시큐리티 인증 실행
            var authToken = new UsernamePasswordAuthenticationToken(req.username, req.password);
            var auth = authenticationManager.authenticate(authToken);

            // 2. principal(CustomUserDetails) 꺼내기
            var userDetails = (CustomUserDetails) auth.getPrincipal();

            // 3. JWT 발급
            String token = provider.createToken(
                    userDetails.getUsername(),
                    Map.of("role", userDetails.getEmployee().getRole().name())
            );

            return ResponseEntity.ok(Map.of("accessToken", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "아이디 또는 비밀번호가 올바르지 않습니다."));
        } catch (Exception e) {
            // 그 외 서버 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류가 발생했습니다. 관리자에게 문의하세요."));
        }
    }

    @Data
    public static class LoginReq {
        private String username;  // loginId or email
        private String password;
    }
}

