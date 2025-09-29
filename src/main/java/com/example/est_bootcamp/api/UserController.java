package com.example.est_bootcamp.api;

import com.example.est_bootcamp.repo.UserAccountMapper;
import com.example.est_bootcamp.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserAccountMapper userAccountMapper; // MyBatis 매퍼 주입

    // ✅ 전체 사용자 목록 페이지
    @GetMapping
    public String list(Model model) {
        List<UserAccount> users = userAccountMapper.findAll();
        model.addAttribute("users", users);
        return "user-list"; // templates/user-list.html
    }

    // ✅ 단일 사용자 상세보기
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long usNo, Model model) {
        UserAccount user = userAccountMapper.findById(usNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + usNo));
        model.addAttribute("user", user);
        return "user-detail"; // templates/user-detail.html
    }

    // ✅ 사용자 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new UserAccount());
        return "user-form"; // templates/user-form.html (추가 필요)
    }

    // ✅ 사용자 등록 처리
    @PostMapping
    public String create(@ModelAttribute UserAccount user) {
        userAccountMapper.insert(user);
        return "redirect:/users";
    }

    // ✅ 사용자 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long usNo, Model model) {
        UserAccount user = userAccountMapper.findById(usNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + usNo));
        model.addAttribute("user", user);
        return "user-form"; // 같은 form 재사용
    }

    // ✅ 사용자 수정 처리
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long usNo, @ModelAttribute UserAccount user) {
        user.setUsNo(usNo);
        userAccountMapper.update(user);
        return "redirect:/users";
    }

    // ✅ 사용자 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long usNo) {
        userAccountMapper.delete(usNo);
        return "redirect:/users";
    }

}