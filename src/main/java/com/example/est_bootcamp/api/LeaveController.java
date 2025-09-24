package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.service.EmployeeService;
import com.example.est_bootcamp.service.LeaveService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;
    private final EmployeeService employeeService;

    /**
     * 휴가 신청
     */
    @PostMapping("/submit")
    public LeaveRequest submit(@RequestBody SubmitReq req) {
        Employee requester = employeeService.getById(req.requesterId); // ✅ 수정
        Employee approver = employeeService.getById(req.approverId);   // ✅ 수정

        return leaveService.submit(
                requester,
                req.leaveTypeCode,
                approver,
                req.startDate,
                req.endDate
        );
    }

    /**
     * 휴가 승인
     */
    @PostMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id, @RequestParam Long approverId) {
        Employee approver = employeeService.getById(approverId); // ✅ 수정
        return leaveService.approve(id, approver);
    }

    /**
     * 휴가 반려
     */
    @PostMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id, @RequestParam Long approverId) {
        Employee approver = employeeService.getById(approverId); // ✅ 수정
        return leaveService.reject(id, approver);
    }

    /**
     * 요청 DTO
     */
    @Data
    public static class SubmitReq {
        @NotNull
        Long requesterId;
        @NotNull
        Long approverId;
        @NotNull
        Long leaveTypeCode;
        @NotNull
        LocalDate startDate;
        @NotNull
        LocalDate endDate;
    }
}
