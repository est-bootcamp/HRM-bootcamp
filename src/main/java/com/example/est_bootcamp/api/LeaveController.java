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

    @PostMapping("/submit")
    public LeaveRequest submit(@RequestBody SubmitReq req) {
        Employee requester = employeeService.findById(req.requesterId);
        Employee approver = employeeService.findById(req.approverId);
        return leaveService.submit(requester, req.leaveTypeCode, approver, req.startDate, req.endDate);
    }

    @PostMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id, @RequestParam Long approverId) {
        Employee approver = employeeService.findById(approverId);
        return leaveService.approve(id, approver);
    }

    @PostMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id, @RequestParam Long approverId) {
        Employee approver = employeeService.findById(approverId);
        return leaveService.reject(id, approver);
    }

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