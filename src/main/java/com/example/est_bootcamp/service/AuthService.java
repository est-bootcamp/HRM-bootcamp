package com.example.est_bootcamp.service;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     * @param loginId   로그인 아이디
     * @param rawPassword 평문 비밀번호 (입력값)
     * @param role       권한/역할
     */
    public void register(String loginId, String rawPassword, String role) {
        // 1. 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(rawPassword);

        // 2. UserAccount 엔티티 생성
        UserAccount user = new UserAccount();
        user.setLoginId(loginId);
        user.setPassword(encodedPw);
        user.setUsRole(role);  // UserAccount 클래스에 role 필드가 있어야 함

        // 3. DB 저장
        userAccountMapper.insert(user);
    }

}
