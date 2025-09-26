package com.example.est_bootcamp.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootTest
public class AuthManagerTest {

    @Autowired
    private AuthenticationManager authManager;

    @Test
    void testLogin() {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken("testuser1", "test1234"); // 아이디/비번 입력

        try {
            var authResult = authManager.authenticate(authRequest);
            System.out.println("✅ 인증 성공, Principal=" + authResult.getPrincipal());
            System.out.println("✅ 권한=" + authResult.getAuthorities());
        } catch (Exception e) {
            System.out.println("❌ 인증 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}