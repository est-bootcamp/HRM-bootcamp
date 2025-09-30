package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.leave.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LeaveRequestMapper { //휴가 신청 데이터 접근 인터페이스(DB의 LV_RQ 테이블과 연동)

    Optional<LeaveRequest> findById(@Param("lvId") Long lvId);
        // (조회-단건) 휴가 신청 ID로 1건 조회
        // 데이터가 없을 수 있으므로 Optional로 감싸서 반환(NullPointerException방지)

    List<LeaveRequest> findAll();
        // (조회-전체) 모든 휴가 신청 목록 조회
        // 목록 화면(leaveList.html)에 표시할 데이터 조회
        // use_yn='Y'인 데이터만 조회 (논리적 삭제된 데이터 제외)

    void insert(LeaveRequest leave);
        // 새로운 휴가 신청 등록
        // leaveForm.html에서 데이터 저장 시 사용

    void update(LeaveRequest leave);
        // 휴가 신청 논리적 삭제
        // 목록에서 삭제 버튼 클릭 시 사용
        // use_yn='N'으로 UPDATE

    void delete(@Param("lvId") Long lvId);
        // LeaveService 에서 delete 메서드 사용중
}
