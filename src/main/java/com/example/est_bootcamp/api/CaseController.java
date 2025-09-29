package com.example.est_bootcamp.api;

import com.example.est_bootcamp.dto.Case;
import com.example.est_bootcamp.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CaseController {

    @Autowired
    private CaseService caseService;

    // CREATE - 사건 생성
    @PostMapping("/case")
    public ResponseEntity<String> createCase(@RequestBody Case cs) {
        caseService.createCase(cs);
        return ResponseEntity.ok("Case created successfully");
    }

    // READ - 전체 사건 조회
    @GetMapping("/cases")
    public ResponseEntity<List<Case>> getAllCases() {
        List<Case> cases = caseService.getAllCases();
        return ResponseEntity.ok(cases);
    }

    // READ - 단일 사건 조회
    @GetMapping("/case/{csId}")
    public ResponseEntity<Case> getCaseById(@PathVariable Long csId) {
        Case caseData = caseService.getCaseById(csId);
        if (caseData != null) {
            return ResponseEntity.ok(caseData);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE - 사건 수정
    @PutMapping("/case/{csId}")
    public ResponseEntity<String> updateCase(@PathVariable Long csId, @RequestBody Case cs) {
        cs.setCsId(csId);
        caseService.updateCase(cs);
        return ResponseEntity.ok("Case updated successfully");
    }

    // DELETE - 사건 삭제
    @DeleteMapping("/case/{csId}")
    public ResponseEntity<String> deleteCase(@PathVariable Long csId) {
        caseService.deleteCase(csId);
        return ResponseEntity.ok("Case deleted successfully");
    }
}