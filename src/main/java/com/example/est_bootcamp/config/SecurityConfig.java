package com.example.est_bootcamp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration // Spring 설정 클래스임을 명시
@EnableWebSecurity // Spring Security 웹 보안 기능 활성화
@EnableMethodSecurity // @PreAuthorize, @Secured 등 메서드 단위 권한 제어 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Spring Security의 핵심 보안 필터 체인을 정의
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 요청 URL 별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 정적 리소스(css/js/img 등)는 누구나 접근 가능
                        .requestMatchers(
                                "/login", "/doLogin", "/css/**", "/js/**", "/dist/**", "/plugins/**", "/images/**", "/webjars/**"
                        ).permitAll()
                        // /main은 로그인된 사용자만 접근 가능
                        .requestMatchers("/main").authenticated()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 2. 권한 부족(403) 발생 시 처리 방식 정의
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 403 발생 시 /error/403 으로 포워드
                            request.getRequestDispatcher("/error/403").forward(request, response);
                        })
                )

                // 3. 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/doLogin") // 로그인 form action 처리 URL
                        .usernameParameter("loginId")   // 로그인 form input name="loginId"
                        .passwordParameter("password")  // 로그인 form input name="password"
                        .defaultSuccessUrl("/main")     // 로그인 성공 시 이동 경로
                        .failureUrl("/login?error=true")// 로그인 실패 시 이동 경로
                        .permitAll() // 로그인 페이지 접근은 누구나 가능
                )

                // 4. 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청 URL
                        .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 시 이동
                );

        return http.build(); // SecurityFilterChain 객체 생성
    }

    /**
     * AuthenticationManager Bean
     * - 사용자 인증을 처리하는 핵심 컴포넌트
     * - AuthenticationConfiguration을 통해 스프링 내부 설정과 PasswordEncoder 연동
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    /**
     * PasswordEncoder Bean
     * - 비밀번호 암호화 및 검증을 담당
     * - BCrypt는 보안 강도가 높아 Spring Security에서 권장됨
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
