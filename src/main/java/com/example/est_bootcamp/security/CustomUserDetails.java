package com.example.est_bootcamp.security;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.user.UserAccount;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserAccount userAccount;
    private final Employee employee;

    public CustomUserDetails(UserAccount userAccount, Employee employee) {
        this.userAccount = userAccount;
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ✅ UserAccount.usRole을 기반으로 권한 부여
        String role = (userAccount.getUsRole() != null)
                ? userAccount.getUsRole().toUpperCase()
                : "USER";
        return List.of((GrantedAuthority) () -> "ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword(); // BCrypt 해시된 비밀번호
    }

    @Override
    public String getUsername() {
        return userAccount.getLoginId();  // 로그인 ID
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userAccount.isEnabled(); // enabled=true 여부
    }

    // 로그인한 사용자의 PK를 반환 (UserAccount 기반)
    public Long getUserId() {
        return userAccount.getUserId();   // UserAccount 안에 usId가 있다고 가정
    }

    // 필요하다면 Employee 기반으로도 가져올 수 있음
    public Long getEmpId() {
        return (employee != null) ? employee.getEmpId() : null;
    }
}
