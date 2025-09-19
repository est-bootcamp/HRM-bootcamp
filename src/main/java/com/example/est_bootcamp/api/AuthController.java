package com.example.est_bootcamp.api;

import com.example.est_bootcamp.security.JwtTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final JwtTokenProvider provider;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginReq req) {
        // TODO: 실제로는 UserAccount + BCrypt 검증 필요
        String token = provider.createToken(req.username, Map.of("role", "STAFF"));
        return Map.of("accessToken", token);
    }

    @Data
    public static class LoginReq {
        String username;
        String password;
    }
}
