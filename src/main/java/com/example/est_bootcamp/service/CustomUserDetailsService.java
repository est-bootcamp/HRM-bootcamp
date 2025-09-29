//package com.example.est_bootcamp.service;
//
//import com.example.est_bootcamp.emp.Employee;
//import com.example.est_bootcamp.repo.EmployeeMapper;
//import com.example.est_bootcamp.repo.UserAccountMapper;
//import com.example.est_bootcamp.security.CustomUserDetails;
//import com.example.est_bootcamp.user.UserAccount;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserAccountMapper userAccountMapper;
//    private final EmployeeMapper employeeMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        UserAccount user = userAccountMapper.findByLoginId(loginId)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + loginId));
//
//        Employee employee = employeeMapper.findByUserNo(user.getUsNo())
//                .orElseThrow(() -> new UsernameNotFoundException("직원 프로필 없음 (usNo=" + user.getUsNo() + ")"));
//
//        return new CustomUserDetails(user, employee);
//    }
//}
