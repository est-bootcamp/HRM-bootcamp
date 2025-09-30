package com.example.est_bootcamp.service;
import com.example.est_bootcamp.leave.LeaveTypeCode;
import com.example.est_bootcamp.repo.LeaveTypeCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveTypeCodeService {

    private final LeaveTypeCodeMapper leaveTypeCodeMapper;

    // 성별에 따른 휴가유형 조회
    public List<LeaveTypeCode> findLeaveTypesByGender(String gender) {
        return leaveTypeCodeMapper.findLeaveTypesByGender(gender);
    }

    // 전체 휴가유형 조회
    public List<LeaveTypeCode> findAllLeaveTypes() {
        return leaveTypeCodeMapper.findAllLeaveTypes();
    }

    // 특정 휴가유형 조회
    public LeaveTypeCode findById(Long leaveTypeId) {
        return leaveTypeCodeMapper.findById(leaveTypeId);
    }

}
