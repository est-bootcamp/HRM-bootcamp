package com.example.est_bootcamp.api;

import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

import java.time.LocalDate;

// 휴가 신청/조회/수정/삭제 기능을 담당
// View를 반환하기 위해 @Controller 사용
@Controller
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {

    // 휴가 관련 비즈니스 로직 처리를 위한 서비스
    private final LeaveService leaveService;

    // 휴가 신청 목록 조회
    // 데이터 조회만 하고 서버 상태를 변경하지 않기 때문에 GET 사용
    @GetMapping
    public String viewLeaves(Model model) {
        // DB에서 모든 휴가 신청 내역을 조회 (use_yn='Y'인 것만)
        List<LeaveRequest> leaveRequests = leaveService.getAllLeaveRequests();

        // 조회한 데이터를 View(leaveList.html)로 전달
        model.addAttribute("leaveRequests", leaveRequests);
        return "leaveList"; // leaveList.html 파일을 렌더링
    }

    // 휴가 등록 폼 화면 표시
    // 빈 폼 화면을 보여주는 것으로, 데이터 변경이 없어서 GET 사용함.
    @GetMapping("/new")
    public String showLeaveForm() {
        // leaveForm.html 파일을 렌더링
        return "leaveForm";
    }

    // 휴가 등록 처리 (데이터 저장) - 서버 상태를 변경하는 작업이므로 POST 사용
    @PostMapping("/new")
    public String createLeave(@RequestParam Long rqsEmpId,      // 신청자 ID
                              @RequestParam Long leaveTypeCode, // 휴가 유형 번호
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // 승인자 ID는 5로 고정
        Long appEmpId = 5L;

        // 서비스 계층에서 휴가 신청 데이터를 DB에 INSERT
        leaveService.submit(rqsEmpId, leaveTypeCode, appEmpId, startDate, endDate);

        // 등록 완료 후 목록 페이지로 리다이렉트
        return "redirect:/leave";
    }


    // 휴가 수정 폼 화면 표시 - 기존 데이터 조회해서 보여주는 것으로 GET 사용, 수정 버튼 시 호출
    @GetMapping("/{id}/edit")
    public String editLeave(@PathVariable Long id, Model model) {
        // DB에서 특정 ID의 휴가 신청 데이터 1건 조회
        LeaveRequest leaveRequest = leaveService.getById(id);

        // 조회한 데이터를 View(leaveEdit.html)로 전달
        model.addAttribute("leaveRequest", leaveRequest);

        // leaveEdit.html 파일을 렌더링
        return "leaveEdit";
    }

    //  휴가 수정 처리 - 서버의 기존 데이터를 변경(UPDATE)하기 때문에 POST 사용
    @PostMapping("/{id}/edit")
    public String updateLeave(@PathVariable Long id,                // 휴가신청 ID
                              @RequestParam Long rqsEmpId,          // 수정된 신청자 ID
                              @RequestParam Long leaveTypeCode,     // 수정된 휴가 유형 번호
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // 서비스 계층에서 해당 ID의 휴가 신청 데이터를 UPDATE
        leaveService.updateLeave(id, rqsEmpId, leaveTypeCode, startDate, endDate);

        return "redirect:/leave"; // 수정 후 목록으로
    }

    // 휴가 신청 삭제 (논리적 삭제) - HTML <a> 태그로 간편하게 호출하기 위해 GET 사용
    @GetMapping("/{id}/delete")
    public String deleteLeave(@PathVariable Long id) {

        // 서비스 계층에서 해당 ID의 use_yn을 'N'으로 UPDATE
        leaveService.delete(id);

        return "redirect:/leave"; // 삭제 후 목록 페이지로 리다이렉트
    }

}
