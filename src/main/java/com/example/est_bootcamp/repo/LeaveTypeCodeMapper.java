package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.leave.LeaveTypeCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveTypeCodeMapper {

    // 성별에 따른 휴가유형 조회
    List<LeaveTypeCode> findLeaveTypesByGender(@Param("gender") String gender);

    // 전체 휴가유형 조회
    List<LeaveTypeCode> findAllLeaveTypes();

    // 특정 휴가유형 조회
    LeaveTypeCode findById(@Param("lvTy") Long lvTy);


}
