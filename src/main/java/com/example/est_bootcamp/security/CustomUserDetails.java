//package com.example.est_bootcamp.security;
//
//import com.example.est_bootcamp.emp.Employee;
//import com.example.est_bootcamp.user.UserAccount;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Getter
//public class CustomUserDetails implements UserDetails {
//
//    private final UserAccount userAccount;
//    private final Employee employee;
//
//    public CustomUserDetails(UserAccount userAccount, Employee employee) {
//        this.userAccount = userAccount;
//        this.employee = employee;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Role enum -> 권한 문자열로 변환
//        return List.of((GrantedAuthority) () -> "ROLE_" + employee.getRole().name());
//    }
//
//    @Override
//    public String getPassword() {
//        return userAccount.getPassword(); // BCrypt 해시된 비밀번호
//    }
//
//    @Override
//    public String getUsername() {
//        return userAccount.getLoginId();  // 로그인 ID
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return userAccount.isEnabled(); // enabled=true 여부
//    }
//}
