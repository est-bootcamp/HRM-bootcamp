package com.example.est_bootcamp.service;

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

    private final LeaveRequestMapper leaveRequestMapper;

    /**
     * 휴가 신청
     */
    public LeaveRequest submit(Long requesterId,
                               Long leaveTypeCode,
                               Long approverId,
                               LocalDate startDate,
                               LocalDate endDate) {

        LeaveRequest request = LeaveRequest.builder()
                .rqsEmpId(requesterId)       // 신청자 ID
                .leaveTypeCode(leaveTypeCode) // 휴가 유형 코드
                .appEmpId(approverId)         // 승인자 ID
                .startDate(startDate)
                .endDate(endDate)
                .status(LeaveStatus.PENDING)  // 처음엔 대기 상태
                .requestDate(LocalDateTime.now())
                .useYn("Y")
                .build();

        leaveRequestMapper.insert(request); // MyBatis insert 호출
        return request; // insert 후 그대로 반환
    }

    /**
     * 휴가 승인
     */
    public LeaveRequest approve(Long leaveId, Long approverId) {
        LeaveRequest request = leaveRequestMapper.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + leaveId));

        request.setAppEmpId(approverId);
        request.setStatus(LeaveStatus.APPROVED);
        request.setModDate(LocalDateTime.now());

        leaveRequestMapper.update(request);
        return request;
    }

    /**
     * 휴가 반려
     */
    public LeaveRequest reject(Long leaveId, Long approverId) {
        LeaveRequest request = leaveRequestMapper.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + leaveId));

        request.setAppEmpId(approverId);
        request.setStatus(LeaveStatus.REJECTED);
        request.setModDate(LocalDateTime.now());

        leaveRequestMapper.update(request);
        return request;
    }
}
