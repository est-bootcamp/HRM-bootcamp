package com.example.est_bootcamp.test;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.user.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserAccountMapperTest {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Test
    void testFindByLoginId() {
        String loginId = "testuser1"; // DB에 있는 계정
        UserAccount user = userAccountMapper.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자 없음: " + loginId));

        System.out.println("DEBUG >> loginId=" + user.getLoginId());
        System.out.println("DEBUG >> password=" + user.getPassword());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPassword() {
        String raw = "test1234";
        String encoded = "$2b$10$ZsxSkkieGXXmlwf5ZFJc/eiJcVUR6OF7.Dgm0Sbd8r6HH9uqF5K4y";
        System.out.println(passwordEncoder.matches(raw, encoded)); // true가 떠야 함
    }
}
