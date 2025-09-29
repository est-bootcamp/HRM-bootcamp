package com.example.est_bootcamp.api;

import com.example.est_bootcamp.dto.Case;
import com.example.est_bootcamp.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    // 사건 목록 보기
    @GetMapping
    public String getAllCases(Model model) {
        List<Case> cases = caseService.getAllCases();
        model.addAttribute("cases", cases);
        return "case-list"; // templates/case-list.html
    }
}