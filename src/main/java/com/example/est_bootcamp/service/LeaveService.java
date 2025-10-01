package com.example.est_bootcamp.service;

import com.example.est_bootcamp.leave.LeaveRequest;
import com.example.est_bootcamp.leave.LeaveStatus;
import com.example.est_bootcamp.repo.LeaveRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;


/* Controller와 Mapper(DB) 사이에서 비즈니스 로직을 처리*/
/* Controller는 이 서비스를 호출하여 휴가 관련 작업 수행*/
@Service
@RequiredArgsConstructor
public class LeaveService {

    /* DB 접근을 위한 MyBatis Mapper */
    private final LeaveRequestMapper leaveRequestMapper;


    //전체 조회 (모든 휴가 신청 목록 조회) - LeaveController의 viewLeaves()에서 호출, 목록 화면(leaveList.html)
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestMapper.findAll();
    }



    // 새로운 휴가 신청 등록
    public LeaveRequest submit(Long requesterId,
                               Long leaveTypeCode,
                               Long approverId,
                               LocalDate startDate,
                               LocalDate endDate

    ) {
        // Builder 패턴으로 휴가 신청 객체 생성 (null 값 처리가 명확)
        LeaveRequest request = LeaveRequest.builder()
                .rqsEmpId(requesterId)       // 신청자 ID
                .leaveTypeCode(leaveTypeCode) // 휴가 유형 코드
                .appEmpId(approverId)         // 승인자 ID
                .startDate(startDate)         // 휴가 시작일
                .endDate(endDate)             // 휴가 종료일
                .status(LeaveStatus.REQUESTED)  // 상태는 자동으로 "REQUESTED"
                .requestDate(LocalDateTime.now()) // 현재 시간
                .useYn("Y")                        // 사용 여부는 'Y'(활성)
                .build();

        leaveRequestMapper.insert(request);     //MyBatis Mapper를 통해 DB에 INSERT
        return request;         //생성된 객체 반환 (useGeneratedKeys로 lv_id가 채워짐)
    }

    //단건 조회 (수정 화면에 기존 데이터 1건 표시용)- LeaveController의 editLeave()에서 호출
    public LeaveRequest getById(Long id) {
        return leaveRequestMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + id));
    }

    //논리 삭제 (삭제 버튼, DB에서 DELETE하지 않고 use_yn='N'으로 UPDATE)
    public void delete(Long id) {
        leaveRequestMapper.delete(id);
    }

    //휴가 신청 정보 수정
    public void updateLeave(Long id, Long rqsEmpId, Long leaveTypeCode,
                            LocalDate startDate, LocalDate endDate) {

        // 기존 데이터 조회 (없으면 예외 발생)
        LeaveRequest existing = leaveRequestMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴가 신청: " + id));

        // 변경할 필드만 수정 (나머지는 기존 값 유지)
        existing.setRqsEmpId(rqsEmpId);             // 신청자 ID 변경
        existing.setLeaveTypeCode(leaveTypeCode);   // 휴가 유형 번호 변경
        existing.setStartDate(startDate);           // 시작일 변경
        existing.setEndDate(endDate);               // 종료일 변경
        existing.setModDate(LocalDateTime.now());   // 수정일 갱신

        // DB에 UPDATE 실행
        leaveRequestMapper.update(existing);

    }
}
