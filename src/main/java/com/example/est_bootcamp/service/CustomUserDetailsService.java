package com.example.est_bootcamp.service;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.EmployeeMapper;
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
    private final EmployeeMapper employeeMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // 1. UserAccount 조회
        UserAccount user = userAccountMapper.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + loginId));

        // 2. Employee 조회 (직원 프로필)
        Employee employee = employeeMapper.findByUserNo(user.getUsNo())
                .orElseThrow(() -> new UsernameNotFoundException("직원 프로필 없음 (usNo=" + user.getUsNo() + ")"));

        // 3. 비밀번호는 반드시 BCrypt 로 저장된 상태여야 함
        //    (예: "$2a$10$..." 형태) → Spring Security가 자동으로 matches() 수행
        String encodedPw = user.getPassword();
        if (!encodedPw.startsWith("$2a$") && !encodedPw.startsWith("$2b$")) {
            throw new IllegalStateException("DB에 저장된 비밀번호가 BCrypt 형식이 아닙니다. userNo=" + user.getUsNo());
        }

        // 4. CustomUserDetails 리턴
        return new CustomUserDetails(user, employee);
    }

}
