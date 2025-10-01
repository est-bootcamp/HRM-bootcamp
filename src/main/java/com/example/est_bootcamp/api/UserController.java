package com.example.est_bootcamp.api;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users") // 사용자 관련 요청을 처리하는 URL prefix
@RequiredArgsConstructor
public class UserController {

    private final UserAccountMapper userAccountMapper; // MyBatis 매퍼 주입 (DB 연동)

    /**
     * 전체 사용자 목록 조회 페이지
     * - GET /users
     * - DB에서 모든 UserAccount 조회 후 user-list.html 뷰로 전달
     */
    @GetMapping
    public String list(Model model) {
        List<UserAccount> users = userAccountMapper.findAll();
        model.addAttribute("users", users);
        return "user-list"; // templates/user-list.html (목록 화면)
    }

    /**
     * 단일 사용자 상세보기
     * - GET /users/{id}
     * - 존재하지 않을 경우 IllegalArgumentException 발생
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long usNo, Model model) {
        UserAccount user = userAccountMapper.findById(usNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + usNo));
        model.addAttribute("user", user);
        return "user-detail"; // templates/user-detail.html (상세 화면)
    }

    /**
     * 사용자 등록 폼
     * - GET /users/new
     * - 신규 UserAccount 객체를 모델에 담아 뷰에 전달
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new UserAccount());
        return "user-form"; // templates/user-form.html (등록/수정 공용 폼)
    }

    /**
     * 사용자 등록 처리
     * - POST /users
     * - DB에 신규 UserAccount 삽입 후 목록 페이지로 리다이렉트
     */
    @PostMapping
    public String create(@ModelAttribute UserAccount user) {
        userAccountMapper.insert(user);
        return "redirect:/users"; // 등록 후 목록으로 이동
    }

    /**
     * 사용자 수정 폼
     * - GET /users/{id}/edit
     * - 기존 UserAccount 정보를 모델에 담아 form에 바인딩
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long usNo, Model model) {
        UserAccount user = userAccountMapper.findById(usNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + usNo));
        model.addAttribute("user", user);
        return "user-form"; // 같은 form 재사용 (등록/수정)
    }

    /**
     * 사용자 수정 처리
     * - POST /users/{id}/edit
     * - PK(usNo) 기반으로 기존 사용자 정보 업데이트
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long usNo, @ModelAttribute UserAccount user) {
        user.setUsNo(usNo);
        userAccountMapper.update(user);
        return "redirect:/users"; // 수정 후 목록으로 이동
    }

    /**
     * 사용자 삭제 처리
     * - POST /users/{id}/delete
     * - DB에서 해당 PK를 가진 사용자 삭제
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long usNo) {
        userAccountMapper.delete(usNo);
        return "redirect:/users"; // 삭제 후 목록으로 이동
    }

}