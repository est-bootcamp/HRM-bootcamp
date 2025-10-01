package com.example.est_bootcamp.service;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.security.CustomUserDetails;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security에서 사용자 인증을 처리하기 위해 필요한 UserDetailsService 구현체
 * - loadUserByUsername() 메서드를 통해 로그인 시 사용자 정보를 DB에서 조회
 * - 조회된 UserAccount를 기반으로 CustomUserDetails를 생성하여 SecurityContext에 저장
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountMapper userAccountMapper;

    /**
     * 사용자 정보를 loginId 기준으로 조회하여 Spring Security 인증 객체(UserDetails)로 변환
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // UserAccount + Employee JOIN 조회
        UserAccount user = userAccountMapper.findWithEmployeeByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + loginId));

        // 비밀번호 검증 (BCrypt 형식이어야 함)
        String encodedPw = user.getPassword();
        if (encodedPw == null || (!encodedPw.startsWith("$2a$") && !encodedPw.startsWith("$2b$"))) {
            throw new IllegalStateException("DB에 저장된 비밀번호가 BCrypt 형식이 아닙니다. userNo=" + user.getUsNo());
        }

        // CustomUserDetails 생성 (Employee 포함)
        return new CustomUserDetails(user, user.getEmployee());
    }
}
