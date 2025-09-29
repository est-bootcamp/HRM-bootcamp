package com.example.est_bootcamp.test;

import com.example.est_bootcamp.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class CustomUserDetailServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testLoadUserByUsername() {
        // 로그인 시도할 아이디 (DB login_id 컬럼에 실제로 있는 값)
        String testLoginId = "testuser1";

        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(testLoginId);

            System.out.println("👉 로그인 시도 ID = " + testLoginId);
            System.out.println("✅ UserDetails.username = " + userDetails.getUsername());
            System.out.println("✅ UserDetails.password = " + userDetails.getPassword());
            System.out.println("✅ UserDetails 권한 = " + userDetails.getAuthorities());
            System.out.println("✅ UserDetails 활성화 여부 = " + userDetails.isEnabled());

        } catch (Exception e) {
            System.out.println("❌ 예외 발생 클래스: " + e.getClass().getName());
            e.printStackTrace(); // 전체 에러 로그 보기
        }
    }
}
