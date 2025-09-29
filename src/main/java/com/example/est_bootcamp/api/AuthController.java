//package com.example.est_bootcamp.api;
//
//import com.example.est_bootcamp.security.CustomUserDetails;
//import com.example.est_bootcamp.security.JwtTokenProvider;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider provider;
//
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody LoginReq req) {
//        // 1. 스프링 시큐리티 인증 실행
//        var authToken = new UsernamePasswordAuthenticationToken(req.username, req.password);
//        var auth = authenticationManager.authenticate(authToken);
//
//        // 2. principal(CustomUserDetails) 꺼내기
//        var userDetails = (CustomUserDetails) auth.getPrincipal();
//
//        // 3. JWT 발급 (DB에 저장된 role 사용)
//        String token = provider.createToken(
//                userDetails.getUsername(),
//                Map.of("role", userDetails.getEmployee().getRole().name())
//        );
//
//        return Map.of("accessToken", token);
//    }
//
//    @Data
//    public static class LoginReq {
//        private String username;  // loginId or email
//        private String password;
//    }
//}
//
