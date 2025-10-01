package com.example.est_bootcamp.security;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.user.UserAccount;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Spring Security의 UserDetails 구현체
 * - UserAccount(계정 정보) + Employee(직원 정보)를 함께 보관
 * - 인증(Authentication)과 권한(Authorization) 처리에서 사용됨
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final UserAccount userAccount; // DB에서 조회한 사용자 계정 정보
    private final Employee employee;       // 해당 계정과 연결된 직원 정보

    public CustomUserDetails(UserAccount userAccount, Employee employee) {
        this.userAccount = userAccount;
        this.employee = employee;
    }

    /**
     * 사용자 권한(Role) 반환
     * - UserAccount.usRole 값을 기반으로 "ROLE_" prefix 붙여서 반환
     * - Spring Security에서 ROLE_ 접두사 필수
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ✅ UserAccount.usRole을 기반으로 권한 부여
        String role = (userAccount.getUsRole() != null)
                ? userAccount.getUsRole().toUpperCase()
                : "USER";
        return List.of((GrantedAuthority) () -> "ROLE_" + role);
    }

    /**
     * 사용자 비밀번호 반환
     * - 반드시 BCrypt 암호화된 해시 값
     */
    @Override
    public String getPassword() {
        return userAccount.getPassword(); // BCrypt 해시된 비밀번호
    }

    /**
     * 사용자 아이디 반환
     * - 로그인에 사용되는 ID (일반적으로 username, 여기서는 loginId)
     */
    @Override
    public String getUsername() {
        return userAccount.getLoginId();  // 로그인 ID
    }

    /** 계정 만료 여부 (true = 만료되지 않음) */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** 계정 잠김 여부 (true = 잠기지 않음) */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** 비밀번호 만료 여부 (true = 만료되지 않음) */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** 계정 활성화 여부 */
    @Override
    public boolean isEnabled() {
        return userAccount.isEnabled(); // enabled=true 여부
    }

    /**
     * 로그인한 사용자 PK(UserAccount 기반)
     * - 보통 DB에서 관리하는 userId
     */
    public Long getUserId() {
        return userAccount.getUserId();   // UserAccount 안에 usId가 있다고 가정
    }

    /**
     * 직원(Employee) PK 반환
     * - UserAccount와 Employee가 매핑되어 있을 경우 사용
     * - 없으면 null 반환
     */
    public Long getEmpId() {
        return (employee != null) ? employee.getEmpId() : null;
    }

    /** 편의 메서드: 직원 이름 */
    public String getEmpName() {
        return (employee != null) ? employee.getName() : null;
    }

    /** 편의 메서드: 직원 이메일 */
    public String getEmpEmail() {
        return (employee != null) ? employee.getEmail() : null;
    }

    /** 편의 메서드: 권한 문자열만 바로 얻기 */
    public String getRole() {
        return (userAccount.getUsRole() != null)
                ? userAccount.getUsRole().toUpperCase()
                : "USER";
    }

    /** 관리자 여부 */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(userAccount.getUsRole());
    }
}
