package com.example.est_bootcamp.service;

import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.leave.LeaveStatus;
import com.example.est_bootcamp.repo.LeaveRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List; // List 추가

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRequestMapper leaveRequestMapper;


    //전체 조회 (목록 화면)
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestMapper.findAll();
    }



    //등록 (휴가 등록)
    public LeaveRequest submit(Long requesterId,
                               Long leaveTypeCode,
                               Long approverId,
                               LocalDate startDate,
                               LocalDate endDate

    ) {

        LeaveRequest request = LeaveRequest.builder()
                .rqsEmpId(requesterId)       // 신청자 ID
                .leaveTypeCode(leaveTypeCode) // 휴가 유형 코드
                .appEmpId(approverId)         // 승인자 ID
                .startDate(startDate)
                .endDate(endDate)
                .status(LeaveStatus.REQUESTED)  // 자동으로 REQUESTED
                .requestDate(LocalDateTime.now()) // 현재 시간
                .useYn("Y")
                .build();

        leaveRequestMapper.insert(request); // MyBatis insert 호출
        return request; // insert 후 그대로 반환
    }

    //단건 조회 (수정 화면에 기존 데이터 표시용)
    public LeaveRequest getById(Long id) {
        return leaveRequestMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + id));
    }

    //논리 삭제 (삭제 버튼, use_yn을 'N'으로 변경)
    public void delete(Long id) {
        leaveRequestMapper.delete(id);
    }

    //수정 (수정 버튼)
    public void updateLeave(Long id, Long rqsEmpId, Long leaveTypeCode,
                            LocalDate startDate, LocalDate endDate) {

        // 기존 데이터 조회
        LeaveRequest existing = leaveRequestMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + id));

        // 수정 가능한 항목만 변경
        existing.setRqsEmpId(rqsEmpId);
        existing.setLeaveTypeCode(leaveTypeCode);
        existing.setStartDate(startDate);
        existing.setEndDate(endDate);
        existing.setModDate(LocalDateTime.now()); // 수정일 갱신

        leaveRequestMapper.update(existing);

    }
}
