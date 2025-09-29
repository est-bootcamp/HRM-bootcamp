package com.example.est_bootcamp.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // ✅ 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        // templates/login.html 렌더링
        return "login";
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login"; // ✅ 로그인 성공 시 항상 /main 으로 이동
    }

    // ✅ 추가: 메인 페이지로 이동
    @GetMapping("/main")
    public String main() {
        return "main"; // templates/main.html
    }
}
