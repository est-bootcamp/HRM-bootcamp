package com.example.est_bootcamp.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    /**
     * 로그인 페이지 진입
     * - GET /login 요청 시 실행
     * - templates/login.html 뷰 렌더링
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 로그인 화면
    }

    /**
     * 루트("/") 요청 시 로그인 페이지로 리다이렉트
     * - GET /
     * - 항상 로그인 페이지로 이동하도록 설정
     */
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    /**
     * 메인 페이지
     * - GET /main
     * - 로그인 성공 후 접근하는 기본 화면
     * - templates/main.html 렌더링
     */
    @GetMapping("/main")
    public String main() {
        return "main"; // 메인 화면
    }
}
