package com.example.est_bootcamp.api;


import com.example.est_bootcamp.leave.LeaveTypeCode;
import com.example.est_bootcamp.service.LeaveTypeCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/leavetypes")
@RequiredArgsConstructor
public class LeaveTypeCodeController {
    private final LeaveTypeCodeService leaveTypeCodeService;


    // 1. 성별에 따른 휴가유형 조회
    @GetMapping("/gender/{gender}")
    public List<LeaveTypeCode> getLeaveTypesByGender(@PathVariable String gender) {
        return leaveTypeCodeService.findLeaveTypesByGender(gender);
    }

    // 2. 전체 휴가유형 조회
    @GetMapping("/")
    public List<LeaveTypeCode> getAllLeaveTypes() {
        return leaveTypeCodeService.findAllLeaveTypes();
    }

    // 3. 특정 휴가유형 조회
    @GetMapping("/{id}")
    public LeaveTypeCode getLeaveTypeById(@PathVariable Long id) {
        return leaveTypeCodeService.findById(id);
    }

}
