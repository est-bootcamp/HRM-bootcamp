package com.example.est_bootcamp.api;

import com.example.est_bootcamp.dto.Case;
import com.example.est_bootcamp.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    // 사건 목록 보기 (READ - List)
    @GetMapping
    public String getAllCases(Model model) {
        List<Case> cases = caseService.getAllCases();
        model.addAttribute("cases", cases);
        return "case-list"; // templates/case-list.html
    }

    // 사건 상세 보기 (READ - Detail)
    @GetMapping("/{csId}")
    public String getCaseById(@PathVariable Long csId, Model model) {
        Case cs = caseService.getCaseById(csId);
        model.addAttribute("caseDetail", cs);
        return "case-detail"; // templates/case-detail.html
    }

    // 사건 등록 폼 페이지 (CREATE FORM)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("caseForm", new Case());
        return "case-form"; // templates/case-form.html (등록 폼)
    }

    // 사건 등록 처리 (CREATE)
    @PostMapping("/new")
    public String createCase(@ModelAttribute("caseForm") Case cs,
                             RedirectAttributes redirectAttributes) {
        // 등록 시 필요한 기본값 설정
        cs.setRegDate(new java.util.Date()); // 현재 시간
        cs.setRegIp("127.0.0.1"); // 실제로는 request.getRemoteAddr()로 가져와야 함
        cs.setRegUsId(1L); // 실제로는 세션에서 로그인한 사용자 ID를 가져와야 함
        cs.setUseYn("Y");

        caseService.createCase(cs); // 저장
        redirectAttributes.addFlashAttribute("message", "사건이 등록되었습니다.");
        return "redirect:/cases";
    }

    // 사건 수정 폼 페이지 (UPDATE FORM)
    @GetMapping("/{csId}/edit")
    public String showUpdateForm(@PathVariable Long csId, Model model) {
        Case cs = caseService.getCaseById(csId);
        model.addAttribute("caseForm", cs);
        return "case-edit"; // templates/case-edit.html (수정 폼)
    }

    // 사건 수정 처리 (UPDATE)
    @PostMapping("/{csId}/edit")
    public String updateCase(@PathVariable Long csId,
                             @ModelAttribute("caseForm") Case cs,
                             RedirectAttributes redirectAttributes) {
        cs.setCsId(csId); // ID 세팅
        // 수정 시 필요한 기본값 설정
        cs.setModDate(new java.util.Date());
        cs.setModIp("127.0.0.1");
        cs.setModUsId(1L);
        cs.setUseYn("Y"); // 사용여부 유지

        caseService.updateCase(cs);
        redirectAttributes.addFlashAttribute("message", "사건이 수정되었습니다.");
        return "redirect:/cases/" + csId;
    }

    // 사건 삭제 (DELETE) -> 수정예정 use_yn
    @PostMapping("/{csId}/delete")
    public String deleteCase(@PathVariable Long csId,
                             RedirectAttributes redirectAttributes) {
        caseService.deleteCase(csId);
        redirectAttributes.addFlashAttribute("message", "사건이 삭제되었습니다.");
        return "redirect:/cases";
    }
}