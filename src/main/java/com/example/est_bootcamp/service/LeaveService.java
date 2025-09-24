package com.example.est_bootcamp.service;

import com.example.est_bootcamp.common.Role;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.leave.LeaveStatus;
import com.example.est_bootcamp.repo.LeaveRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRequestMapper mapper;

    public LeaveRequest submit(Employee requester, Long leaveTypeCode, Employee approver,
                               LocalDate start, LocalDate end) {
        if (end.isBefore(start)) throw new IllegalArgumentException("end before start");

        if (requester.getRole() == Role.PARTNER || requester.getRole() == Role.OWNER) {
            if (!(approver.getRole() == Role.OWNER)) {
                throw new IllegalStateException("Partner/Owner leave requires OWNER approval");
            }
        } else {
            if (!(approver.getRole() == Role.ADMIN)) {
                throw new IllegalStateException("Staff leave requires ADMIN approval");
            }
        }

        LeaveRequest lv = LeaveRequest.builder()
                .rqsEmpId(requester.getEmpId())   // 신청자 ID
                .appEmpId(approver.getEmpId())    // 승인자 ID
                .leaveTypeCode(leaveTypeCode)
                .startDate(start)
                .endDate(end)
                .status(LeaveStatus.REQUESTED)
                .requestDate(LocalDateTime.now())
                .build();

        mapper.insert(lv);
        return lv;
    }

    public LeaveRequest approve(Long leaveId, Employee approver) {
        LeaveRequest lv = mapper.findById(leaveId).orElseThrow();
        if (!lv.getAppEmpId().equals(approver.getEmpId())) {
            throw new SecurityException("Not the designated approver");
        }
        lv.setStatus(LeaveStatus.APPROVED);
        mapper.update(lv);
        return lv;
    }

    public LeaveRequest reject(Long leaveId, Employee approver) {
        LeaveRequest lv = mapper.findById(leaveId).orElseThrow();
        if (!lv.getAppEmpId().equals(approver.getEmpId())) {
            throw new SecurityException("Not the designated approver");
        }
        lv.setStatus(LeaveStatus.REJECTED);
        mapper.update(lv);
        return lv;
    }
}
