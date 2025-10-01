package com.example.est_bootcamp.service;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 인증/인가 관련 서비스
 * - 회원가입, 비밀번호 암호화, 권한 부여 등의 기능 담당
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    // DB 연동 (MyBatis Mapper)
    private final UserAccountMapper userAccountMapper;

    // Spring Security에서 제공하는 비밀번호 암호화기 (BCrypt 등)
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     */
    public void register(String loginId, String rawPassword, String role) {
        // 1. 비밀번호 암호화 (평문 → BCrypt 해싱된 문자열)
        String encodedPw = passwordEncoder.encode(rawPassword);

        // 2. UserAccount 엔티티 생성 및 값 세팅
        UserAccount user = new UserAccount();
        user.setLoginId(loginId);       // 로그인 아이디
        user.setPassword(encodedPw);    // 암호화된 비밀번호
        user.setUsRole(role);           // 권한(Role) 부여

        // 3. DB에 저장 (INSERT 쿼리 실행)
        userAccountMapper.insert(user);
    }

}
