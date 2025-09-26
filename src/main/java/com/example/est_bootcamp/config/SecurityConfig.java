package com.example.est_bootcamp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (테스트용, 실서비스에서는 활성화 권장)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login", "/doLogin", "/css/**", "/js/**", "/dist/**", "/plugins/**", "/images/**", "/webjars/**"
                        ).permitAll().requestMatchers("/main").authenticated()
                        .anyRequest().authenticated()        // 나머지는 인증 필요
                )

                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")               // GET 요청 → 로그인 폼
                        .loginProcessingUrl("/doLogin")    // POST 요청 → 스프링이 처리
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main")      // 성공 시 이동
                        .failureUrl("/login?error=true")   // 실패 시
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                );

        return http.build();
    }

    // AuthenticationManager Bean (사용자 인증 서비스와 PasswordEncoder 연동)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    // 비밀번호 암호화용 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
