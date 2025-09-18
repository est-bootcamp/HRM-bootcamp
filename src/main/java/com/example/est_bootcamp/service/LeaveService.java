package com.example.est_bootcamp.service;

import com.example.est_bootcamp.common.Role;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.leave.LeaveStatus;
import com.example.est_bootcamp.repo.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class LeaveService {
    private final LeaveRequestRepository repo;

    @Transactional
    public LeaveRequest submit(Employee requester, Long leaveTypeCode, Employee approver,
                               java.time.LocalDate start, java.time.LocalDate end) {
        // 간단 검증
        if (end.isBefore(start)) throw new IllegalArgumentException("end before start");

        // 승인자 규칙(요약):
        // - 변호사: PARTNER/OWNER만 승인
        // - 직원: HR(=ADMIN) 승인 (간단화해서 ADMIN으로 처리)
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
                .id(null) // Generation 전략 전환 권장
                .requester(requester)
                .approver(approver)
                .leaveTypeCode(leaveTypeCode)
                .startDate(start)
                .endDate(end)
                .status(LeaveStatus.REQUESTED)
                .requestDate(LocalDateTime.now())
                .build();
        return repo.save(lv);
    }

    @Transactional
    public LeaveRequest approve(Long leaveId, Employee approver) {
        LeaveRequest lv = repo.findById(leaveId).orElseThrow();
        if (!lv.getApprover().getId().equals(approver.getId())) {
            throw new SecurityException("Not the designated approver");
        }
        lv.setStatus(LeaveStatus.APPROVED);
        return lv;
    }

    @Transactional
    public LeaveRequest reject(Long leaveId, Employee approver) {
        LeaveRequest lv = repo.findById(leaveId).orElseThrow();
        if (!lv.getApprover().getId().equals(approver.getId())) {
            throw new SecurityException("Not the designated approver");
        }
        lv.setStatus(LeaveStatus.REJECTED);
        return lv;
    }
}