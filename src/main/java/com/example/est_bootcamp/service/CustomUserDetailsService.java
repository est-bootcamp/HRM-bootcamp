package com.example.est_bootcamp.service;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.security.CustomUserDetails;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountMapper userAccountMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // ✅ UserAccount + Employee JOIN 조회
        UserAccount user = userAccountMapper.findWithEmployeeByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + loginId));

        // ✅ 비밀번호 검증 (BCrypt 형식이어야 함)
        String encodedPw = user.getPassword();
        if (encodedPw == null || (!encodedPw.startsWith("$2a$") && !encodedPw.startsWith("$2b$"))) {
            throw new IllegalStateException("DB에 저장된 비밀번호가 BCrypt 형식이 아닙니다. userNo=" + user.getUsNo());
        }

        // ✅ CustomUserDetails 생성 (Employee 포함)
        return new CustomUserDetails(user, user.getEmployee());
    }
}
