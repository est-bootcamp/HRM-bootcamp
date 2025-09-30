package com.example.est_bootcamp.api;

import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Model 추가
import java.util.List; // List 추가

import java.time.LocalDate;

@Controller

//휴가 신청 목록
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;

    //목록 조회
    @GetMapping
    public String viewLeaves(Model model) {
        List<LeaveRequest> leaveRequests = leaveService.getAllLeaveRequests();
        model.addAttribute("leaveRequests", leaveRequests);
        return "leaveList"; // leaveList.html 파일을 렌더링
    }

    // 휴가 등록 폼 보여주기 (GET)
    @GetMapping("/new")
    public String showLeaveForm() {
        return "leaveForm";
    }

    // 휴가 등록 처리 (POST)
    @PostMapping("/new")
    public String createLeave(@RequestParam Long rqsEmpId,
                              @RequestParam Long leaveTypeCode,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // 승인자 ID는 5로 고정
        Long appEmpId = 5L;
        leaveService.submit(rqsEmpId, leaveTypeCode, appEmpId, startDate, endDate);

        return "redirect:/leave"; // 등록 후 목록으로 이동
    }


    // 수정 페이지 이동 (단건 조회 필요)
    @GetMapping("/{id}/edit")
    public String editLeave(@PathVariable Long id, Model model) {
        LeaveRequest leaveRequest = leaveService.getById(id);
        model.addAttribute("leaveRequest", leaveRequest);
        return "leaveEdit"; // leaveEdit.html로 이동
    }

    // 수정 처리
    @PostMapping("/{id}/edit")
    public String updateLeave(@PathVariable Long id,
                              @RequestParam Long rqsEmpId,
                              @RequestParam Long leaveTypeCode,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        leaveService.updateLeave(id, rqsEmpId, leaveTypeCode, startDate, endDate);

        return "redirect:/leave"; // 수정 후 목록으로
    }

    // 삭제 처리
    @GetMapping("/{id}/delete")
    public String deleteLeave(@PathVariable Long id) {
        leaveService.delete(id);
        return "redirect:/leave"; // 삭제 후 목록 페이지로 리다이렉트
    }

}
